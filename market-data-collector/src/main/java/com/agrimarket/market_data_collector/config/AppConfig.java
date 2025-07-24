// File: src/main/java/com/agrimarket/marketdatacollector/config/AppConfig.java
package com.agrimarket.market_data_collector.config; // Or your package with underscores

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}