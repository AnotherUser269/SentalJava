package com.example.services;

import com.example.core.Account;
import com.example.core.Transaction;
import com.example.core.TransferMessage;
import com.example.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.example.core.TransferStatus;
import com.example.repositories.AccountRepository;

import java.util.List;

public class ConsumerService {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final PlatformTransactionManager transactionManager;
  private final ObjectMapper objectMapper;

  public ConsumerService(AccountRepository accountRepository,
                         TransactionRepository transactionRepository,
                         PlatformTransactionManager transactionManager) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.transactionManager = transactionManager;
    this.objectMapper = new ObjectMapper();
  }

  @KafkaListener(topics = "bank-transactions", groupId = "bank-processors")
  public void listen(List<String> messages, Acknowledgment ack) {
    for (String message : messages) {
      processMessage(message);
    }
    ack.acknowledge();
  }

  private void processMessage(String message) {
    logger.info("Starting processing: {}", message);

    try {
      TransferMessage transfer = objectMapper.readValue(message, TransferMessage.class);
      processTransaction(transfer);
    } catch (Exception e) {
      logger.error("Got error with processing: {}", e.getMessage());
    }
  }

  private void processTransaction(TransferMessage transfer) {
    TransactionStatus status = null;
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();

    try {
      status = transactionManager.getTransaction(def);

      Account fromAcc = accountRepository.findByIdForUpdate(transfer.getFrom());
      Account toAcc = accountRepository.findByIdForUpdate(transfer.getTo());

      if (fromAcc == null || toAcc == null) {
        logger.error("Error: account wasn't found for transaction: {}", transfer.getId());
        transactionManager.rollback(status);
        return;
      }

      if (fromAcc.getBalance().compareTo(transfer.getAmount()) < 0) {
        logger.error("Error: bot enough money for transaction: {}", transfer.getId());
        transactionManager.rollback(status);
        return;
      }

      fromAcc.setBalance(fromAcc.getBalance().subtract(transfer.getAmount()));
      toAcc.setBalance(toAcc.getBalance().add(transfer.getAmount()));
      accountRepository.updateBalance(fromAcc.getId(), fromAcc.getBalance());
      accountRepository.updateBalance(toAcc.getId(), toAcc.getBalance());

      Transaction tx = new Transaction();
      tx.setId(transfer.getId());
      tx.setFromAccountId(transfer.getFrom());
      tx.setToAccountId(transfer.getTo());
      tx.setAmount(transfer.getAmount());
      tx.setStatus(TransferStatus.DONE);
      transactionRepository.save(tx);

      transactionManager.commit(status);
      logger.info("Success: {}", transfer.getId());

    } catch (Exception e) {
      if (status != null) {
        transactionManager.rollback(status);
      }
      logger.error("Error: {}: {}", transfer.getId(), e.getMessage());

      saveErrorTransaction(transfer);
    }
  }

  private void saveErrorTransaction(TransferMessage transfer) {
    try {
      Transaction tx = new Transaction();
      tx.setId(transfer.getId());
      tx.setFromAccountId(transfer.getFrom());
      tx.setToAccountId(transfer.getTo());
      tx.setAmount(transfer.getAmount());
      tx.setStatus(TransferStatus.ERROR);
      transactionRepository.save(tx);
      logger.info("Transaction: {} saved 'error'", transfer.getId());
    } catch (Exception e) {
      logger.error("Error for saving info for transaction {}: {}", transfer.getId(), e.getMessage());
    }
  }
}
