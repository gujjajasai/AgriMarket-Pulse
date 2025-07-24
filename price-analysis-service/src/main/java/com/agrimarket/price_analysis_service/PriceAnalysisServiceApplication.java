// File: src/main/java/com/agrimarket/priceanalysisservice/PriceAnalysisServiceApplication.java
package com.agrimarket.price_analysis_service; // Or your package with underscores

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // <-- ADD THIS IMPORT

@SpringBootApplication
@EnableScheduling // <-- ADD THIS ANNOTATION
public class PriceAnalysisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceAnalysisServiceApplication.class, args);
	}
}