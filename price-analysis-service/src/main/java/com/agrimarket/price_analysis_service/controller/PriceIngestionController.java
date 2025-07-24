// File: src/main/java/com/agrimarket/priceanalysisservice/controller/PriceIngestionController.java
package com.agrimarket.price_analysis_service.controller;

import com.agrimarket.price_analysis_service.dto.MarketDataDTO;
import com.agrimarket.price_analysis_service.entity.PriceRecord;
import com.agrimarket.price_analysis_service.repository.PriceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DataIntegrityViolationException; // <-- ADD THIS IMPORT

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceIngestionController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final PriceRepository priceRepository;

    public PriceIngestionController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @PostMapping("/ingest")
    public ResponseEntity<String> ingestPrices(@RequestBody List<MarketDataDTO> marketDataList) {
        if (marketDataList == null || marketDataList.isEmpty()) {
            return ResponseEntity.badRequest().body("Received empty or null list.");
        }

        System.out.println("Received " + marketDataList.size() + " records to ingest.");

        List<PriceRecord> recordsToSave = new ArrayList<>();
        int failedParsingRecords = 0;
        int duplicateRecords = 0; // <-- New counter for duplicates

        for (MarketDataDTO dto : marketDataList) {
            try {
                PriceRecord record = new PriceRecord();
                record.setState(dto.getState());
                record.setDistrict(dto.getDistrict());
                record.setMarket(dto.getMarket());
                record.setCommodity(dto.getCommodity());

                record.setMinPrice(parsePriceToBigDecimal(dto.getMinPrice()));
                record.setMaxPrice(parsePriceToBigDecimal(dto.getMaxPrice()));
                record.setModalPrice(parsePriceToBigDecimal(dto.getModalPrice()));

                record.setScrapedDate(parseDateString(dto.getDate()));
                recordsToSave.add(record);
            } catch (NumberFormatException | DateTimeParseException e) {
                System.err.println("Skipping record due to invalid format for market " + dto.getMarket() + ", commodity " + dto.getCommodity() + " (Date: " + dto.getDate() + ", MinPrice: " + dto.getMinPrice() + "): " + e.getMessage());
                failedParsingRecords++;
            }
        }

        // --- Handle duplicates during save ---
        for (PriceRecord record : recordsToSave) {
            try {
                priceRepository.save(record); // Save one by one to catch individual duplicates
            } catch (DataIntegrityViolationException e) {
                System.out.println("Skipping duplicate record: " + record.getCommodity() + " in " + record.getMarket() + " on " + record.getScrapedDate());
                duplicateRecords++;
            }
        }

        // Changed message to reflect different types of failures
        System.out.println("Ingestion complete. Saved: " + (recordsToSave.size() - duplicateRecords) +
                ", Duplicates Skipped: " + duplicateRecords +
                ", Parsing Failed: " + failedParsingRecords + ".");

        return ResponseEntity.ok("Ingestion complete. Saved: " + (recordsToSave.size() - duplicateRecords) +
                ", Duplicates Skipped: " + duplicateRecords +
                ", Parsing Failed: " + failedParsingRecords + ".");
    }

    private BigDecimal parsePriceToBigDecimal(String priceString) {
        if (priceString == null || priceString.trim().isEmpty() || priceString.equals("-")) {
            return BigDecimal.ZERO;
        }
        String cleanedPriceString = priceString.replace(",", "");
        try {
            return new BigDecimal(cleanedPriceString);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid price string: '" + priceString + "'");
        }
    }

    private LocalDate parseDateString(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new DateTimeParseException("Date string is null or empty", dateString, 0);
        }
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
}