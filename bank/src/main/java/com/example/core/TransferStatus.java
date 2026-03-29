package com.example.core;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransferStatus {

  DONE("done"),
  ERROR("error");

  private final String value;

  TransferStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
