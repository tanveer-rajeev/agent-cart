package com.tanveer.commonlib.domain;

public interface EventRepository<T extends DomainEvent> {
  void saveEvent(T event);
}
