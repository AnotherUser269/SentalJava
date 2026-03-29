package com.example.repositories;

import com.example.core.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {

  private final JdbcTemplate jdbcTemplate;

  public TransactionRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void save(Transaction transaction) {
    jdbcTemplate.update(
        "INSERT INTO transactions (id, from_account_id, to_account_id, amount, status) " +
            "VALUES (?, ?, ?, ?, ?::transfer_status)",
        transaction.getId(),
        transaction.getFromAccountId(),
        transaction.getToAccountId(),
        transaction.getAmount(),
        transaction.getStatus().getValue()
    );
  }
}
