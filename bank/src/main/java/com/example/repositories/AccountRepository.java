package com.example.repositories;

import com.example.core.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepository {

  private final JdbcTemplate jdbcTemplate;

  public AccountRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Account> findAll() {
    return jdbcTemplate.query("SELECT id, balance FROM accounts", new AccountRowMapper());
  }

  public Account findByIdForUpdate(Long id) {
    List<Account> accounts = jdbcTemplate.query(
        "SELECT id, balance FROM accounts WHERE id = ? FOR UPDATE",
        new Object[]{id},
        new AccountRowMapper()
    );
    return accounts.isEmpty() ? null : accounts.getFirst();
  }

  public void updateBalance(Long id, BigDecimal balance) {
    jdbcTemplate.update("UPDATE accounts SET balance = ? WHERE id = ?", balance, id);
  }

  public void save(Account account) {
    jdbcTemplate.update(
        "INSERT INTO accounts (id, balance) VALUES (?, ?)",
        account.getId(), account.getBalance()
    );
  }

  public void saveAll(List<Account> accounts) {
    for (Account acc : accounts) {
      save(acc);
    }
  }

  private static class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Account(rs.getLong("id"), rs.getBigDecimal("balance"));
    }
  }
}
