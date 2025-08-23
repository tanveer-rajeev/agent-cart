package com.tanveer.inventoryservice.domain;

public enum EventType {
  INVENTORY_CREATED("inventory-created"),
  INVENTORY_RESERVED("inventory.reserved"),
  INVENTORY_RELEASED("inventory.released"),
  INVENTORY_ADJUST("inventory.adjust");


  private final String value;

  EventType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
  }
