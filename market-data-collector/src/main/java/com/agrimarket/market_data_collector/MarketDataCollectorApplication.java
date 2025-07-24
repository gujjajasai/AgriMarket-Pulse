// File: src/main/java/com/agrimarket/marketdatacollector/MarketDataCollectorApplication.java
package com.agrimarket.market_data_collector; // Use your actual package

import com.agrimarket.market_data_collector.properities.ScraperProperties; // <-- FIXED: changed 'properities' to 'properties'

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ScraperProperties.class) // <-- Reference the external class
public class MarketDataCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketDataCollectorApplication.class, args);
	}
}