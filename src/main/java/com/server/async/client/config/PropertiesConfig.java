package com.server.async.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.api")
public class PropertiesConfig {

    private String baseUrl;
}
