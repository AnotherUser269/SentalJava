package com.example.services;

import com.example.core.Account;
import com.example.core.TransferMessage;
import com.example.repositories.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ProducerService {

  private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

  private final AccountRepository accountRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final Map<Long, Account> accountsCache = new HashMap<>();
  private final Random random = new Random();

  public ProducerService(AccountRepository accountRepository, KafkaTemplate<String, String> kafkaTemplate) {
    this.accountRepository = accountRepository;
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = new ObjectMapper();
  }

  @PostConstruct
  public void init() {
    if (!"producer".equals(System.getenv("APP_ROLE"))) {
      return;
    }

    List<Account> allAccounts = accountRepository.findAll();

    if (allAccounts.isEmpty()) {
      logger.info("Empty, generating 1000 accounts...");
      List<Account> newAccounts = new ArrayList<>();

      for (long i = 1; i <= 1000; i++) {
        Account acc = new Account(i, new BigDecimal("10000.00"));
        newAccounts.add(acc);
      }

      accountRepository.saveAll(newAccounts);
      allAccounts = newAccounts;

      logger.info("Created.");
    } else {
      logger.info("Found accounts в БД, loading in a Map...");
    }

    for (Account acc : allAccounts) {
      accountsCache.put(acc.getId(), acc);
    }
    logger.info("The Map is full.");
  }

  @Scheduled(fixedDelay = 200)
  public void generateAndSendTransfer() {
    if (!"producer".equals(System.getenv("APP_ROLE"))) {
      return;
    }

    if (accountsCache.size() < 2) return;

    List<Long> ids = new ArrayList<>(accountsCache.keySet());
    Long fromId = ids.get(random.nextInt(ids.size()));
    Long toId;
    do {
      toId = ids.get(random.nextInt(ids.size()));
    } while (fromId.equals(toId));

    BigDecimal amount = new BigDecimal(random.nextInt(100) + 1 + "." + random.nextInt(99));
    String transferId = UUID.randomUUID().toString();

    TransferMessage message = new TransferMessage(transferId, fromId, toId, amount);

    try {
      String json = objectMapper.writeValueAsString(message);
      kafkaTemplate.send("bank-transactions", String.valueOf(fromId), json);
      logger.info("Sent: id={}, from={}, to={}, amount={}", transferId, fromId, toId, amount);
    } catch (Exception e) {
      logger.error("Got error: {}", e.getMessage());
    }
  }
}
