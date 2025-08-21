package com.tanveer.productservice.domain;

public enum EventType {
  PRODUCT_CREATED("PRODUCT_CREATED"),
  PRODUCT_UPDATED("PRODUCT_UPDATED");

  private final String value;

  EventType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
