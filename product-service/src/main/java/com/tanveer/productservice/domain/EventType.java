package com.tanveer.productservice.domain;

public enum EventType {
  PRODUCT_CREATED("product-created"),
  PRODUCT_UPDATED("product-updated");

  private final String value;

  EventType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
