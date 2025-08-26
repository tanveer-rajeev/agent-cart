package com.tanveer.commonlib.domain;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
  String getEventType();
  Instant getOccurredAt();
  String getAggregateId();
  String getAggregateType();
}
