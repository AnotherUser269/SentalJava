package com.example.core;

import java.math.BigDecimal;

public class Account {

  private Long id;
  private BigDecimal balance;

  public Account(Long id, BigDecimal balance) {
    this.id = id;
    this.balance = balance;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
