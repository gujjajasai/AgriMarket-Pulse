// File: src/main/java/com/agrimarket/priceanalysisservice/service/AlertingService.java
package com.agrimarket.price_analysis_service.service;

import com.agrimarket.price_analysis_service.dto.ArbitrageAlert; // Ensure this import is present
import com.agrimarket.price_analysis_service.entity.PriceRecord;
import com.agrimarket.price_analysis_service.repository.PriceRepository;
import org.springframework.scheduling.annotation.Scheduled; // For scheduling the task
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AlertingService {

    private final PriceRepository priceRepository;
    private final BigDecimal ARBITRAGE_THRESHOLD = new BigDecimal("100.00"); // Define your threshold for an alert

    public AlertingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * This scheduled method will run periodically to check for arbitrage opportunities.
     * Runs every 5 minutes for testing. In production, maybe once a day after scraping.
     */
    @Scheduled(fixedRate = 300000) // 5 minutes
    public void checkForArbitrageOpportunities() {
        System.out.println("--- Alerting Service: Checking for arbitrage opportunities for today: " + LocalDate.now() + " ---");
        LocalDate today = LocalDate.now();

        // Get all unique commodities that have data for today
        List<String> commoditiesWithData = priceRepository.findDistinctCommoditiesByScrapedDate(today);

        if (commoditiesWithData.isEmpty()) {
            System.out.println("No commodity data available for today to analyze for alerts.");
            return;
        }

        for (String commodity : commoditiesWithData) {
            // Fetch all records for this commodity on today's date
            List<PriceRecord> records = priceRepository.findByCommodityIgnoreCaseAndScrapedDate(commodity, today);

            if (records.size() < 2) { // Need at least two markets to compare
                System.out.println("Skipping alert check for " + commodity + " - less than 2 markets with data today.");
                continue;
            }

            Optional<PriceRecord> minRecordOpt = records.stream()
                    .filter(r -> r.getModalPrice() != null)
                    .min(Comparator.comparing(PriceRecord::getModalPrice));

            Optional<PriceRecord> maxRecordOpt = records.stream()
                    .filter(r -> r.getModalPrice() != null)
                    .max(Comparator.comparing(PriceRecord::getModalPrice));

            if (minRecordOpt.isEmpty() || maxRecordOpt.isEmpty()) {
                System.out.println("Skipping alert check for " + commodity + " - no valid min/max prices found.");
                continue;
            }

            PriceRecord minRecord = minRecordOpt.get();
            PriceRecord maxRecord = maxRecordOpt.get();

            BigDecimal priceDifference = maxRecord.getModalPrice().subtract(minRecord.getModalPrice());

            // Check if the price difference exceeds our defined threshold
            if (priceDifference.compareTo(ARBITRAGE_THRESHOLD) > 0) {
                ArbitrageAlert alert = new ArbitrageAlert(
                        commodity,
                        today,
                        minRecord.getModalPrice(),
                        minRecord.getMarket(),
                        minRecord.getDistrict(), // Assuming district is part of the record
                        maxRecord.getModalPrice(),
                        maxRecord.getMarket(),
                        maxRecord.getDistrict(), // Assuming district is part of the record
                        priceDifference,
                        String.format("Significant price difference detected for %s! Min: %s in %s (%s), Max: %s in %s (%s). Difference: %s",
                                commodity,
                                minRecord.getModalPrice(), minRecord.getMarket(), minRecord.getDistrict(),
                                maxRecord.getModalPrice(), maxRecord.getMarket(), maxRecord.getDistrict(),
                                priceDifference)
                );
                System.out.println("--- ALERT! --- " + alert.getMessage()); // This is your console alert!
                // In a real app, you would send email/SMS here (e.g., alertService.sendEmail(alert))
            } else {
                System.out.println("No significant arbitrage for " + commodity + " today. Difference: " + priceDifference);
            }
        }
        System.out.println("--- Alerting Service: Check completed. ---");
    }
}