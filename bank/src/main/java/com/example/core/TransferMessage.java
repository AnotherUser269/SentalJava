package com.example.core;

import java.math.BigDecimal;

public class TransferMessage {

  private String id;
  private Long from;
  private Long to;
  private BigDecimal amount;

  public TransferMessage(String id, Long from, Long to, BigDecimal amount) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.amount = amount;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getFrom() {
    return from;
  }

  public void setFrom(Long from) {
    this.from = from;
  }

  public Long getTo() {
    return to;
  }

  public void setTo(Long to) {
    this.to = to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
