package com.tanveer.commonlib.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;


@Data
@SuperBuilder
@AllArgsConstructor
@MappedSuperclass
public abstract class EventEntity {

  protected EventEntity() {}

  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Lob
  private String payload;

  @Column(name = "aggregate_type")
  private String aggregateType;

  @Column(name = "event_type")
  private String eventType;

  @Column(name = "aggregate_id")
  private UUID aggregateId;

  @Column(name = "occurred_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant occurredAt;

  private boolean published;

  @Column(name = "published_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant publishedAt;

  public void markPublished() {
    this.published = true;
    this.publishedAt = Instant.now();
  }

}
