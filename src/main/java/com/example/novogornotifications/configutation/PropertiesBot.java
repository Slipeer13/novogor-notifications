package com.example.novogornotifications.configutation;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public record PropertiesBot(String token, String username) {
}
