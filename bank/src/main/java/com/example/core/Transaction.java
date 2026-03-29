package com.example.core;

import java.math.BigDecimal;

public class Transaction {

  private String id;
  private Long fromAccountId;
  private Long toAccountId;
  private BigDecimal amount;
  private TransferStatus status;

  public Transaction() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(Long fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public Long getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(Long toAccountId) {
    this.toAccountId = toAccountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public TransferStatus getStatus() {
    return status;
  }

  public void setStatus(TransferStatus status) {
    this.status = status;
  }
}
