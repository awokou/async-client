package com.server.async.client.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(PropertiesConfig.class)
public class WebClientConfig {

    private static final int MAX_MEMORY_SIZE = 16 * 1024 * 1024; // 16 MB

    @Bean
    public WebClient.Builder webClientBuilder() {
        // Configurer la mémoire maximale pour le body
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(MAX_MEMORY_SIZE))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies);
    }

    @Bean
    public WebClient webClient(
            WebClient.Builder builder,
            PropertiesConfig propertiesConfig) {

        return builder
                .baseUrl(propertiesConfig.getBaseUrl())
                .build();
    }
}
