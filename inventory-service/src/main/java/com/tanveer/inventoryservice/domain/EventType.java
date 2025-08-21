package com.tanveer.inventoryservice.domain;

public enum EventType {
  PRODUCT_CREATED("product-created"),
  INVENTORY_RESERVED("inventory.reserved"),
  INVENTORY_RELEASED("inventory.released"),
  INVENTORY_REPLENISHED("inventory.replenished");

  private final String value;

  EventType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
  }
