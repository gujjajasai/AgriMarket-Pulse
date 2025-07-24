// File: src/main/java/com/agrimarket/priceanalysisservice/service/PriceAnalysisService.java
package com.agrimarket.price_analysis_service.service;

import com.agrimarket.price_analysis_service.dto.PriceAnalysisResult; // Ensure this import is present
import com.agrimarket.price_analysis_service.entity.PriceRecord;
import com.agrimarket.price_analysis_service.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator; // Ensure this import is present
import java.util.List;
import java.util.Optional; // Ensure this import is present

@Service
public class PriceAnalysisService {

    private final PriceRepository priceRepository;

    public PriceAnalysisService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Finds the overall min, max, and difference for a commodity across all markets on a given date.
     * @param commodity The commodity variety (e.g., "Other", "Red").
     * @param scrapedDate The date for which to perform analysis.
     * @return Optional<PriceAnalysisResult> containing the findings, or Optional.empty() if no data.
     */
    public Optional<PriceAnalysisResult> analyzeCommodityPrices(String commodity, LocalDate scrapedDate) {
        // Fetch all records for the given commodity and date
        List<PriceRecord> records = priceRepository.findByCommodityIgnoreCaseAndScrapedDate(commodity, scrapedDate);

        if (records.isEmpty()) {
            return Optional.empty(); // No data for this commodity and date
        }

        // Find the record with the minimum modal price
        Optional<PriceRecord> minRecordOpt = records.stream()
                .filter(r -> r.getModalPrice() != null) // Filter out records with null prices
                .min(Comparator.comparing(PriceRecord::getModalPrice));

        // Find the record with the maximum modal price
        Optional<PriceRecord> maxRecordOpt = records.stream()
                .filter(r -> r.getModalPrice() != null) // Filter out records with null prices
                .max(Comparator.comparing(PriceRecord::getModalPrice));

        if (minRecordOpt.isEmpty() || maxRecordOpt.isEmpty()) {
            // This could happen if all modal prices are null after filtering
            return Optional.empty();
        }

        PriceRecord minRecord = minRecordOpt.get();
        PriceRecord maxRecord = maxRecordOpt.get();

        BigDecimal priceDifference = maxRecord.getModalPrice().subtract(minRecord.getModalPrice());

        return Optional.of(new PriceAnalysisResult(
                commodity,
                scrapedDate.toString(), // Convert LocalDate back to String for DTO consistency
                minRecord.getModalPrice(),
                minRecord.getMarket(),
                maxRecord.getModalPrice(),
                maxRecord.getMarket(),
                priceDifference
        ));
    }
}