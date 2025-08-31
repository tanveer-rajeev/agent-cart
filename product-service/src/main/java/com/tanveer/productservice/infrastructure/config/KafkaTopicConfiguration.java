package com.tanveer.productservice.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "topic")
public class KafkaTopicConfiguration {
  private Map<String, String> map;
}
