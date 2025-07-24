// File: src/main/java/com/agrimarket/priceanalysisservice/controller/query/PriceQueryController.java
package com.agrimarket.price_analysis_service.controller.query;

import com.agrimarket.price_analysis_service.entity.PriceRecord;
import com.agrimarket.price_analysis_service.repository.PriceRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.agrimarket.price_analysis_service.dto.PriceAnalysisResult;
import com.agrimarket.price_analysis_service.service.PriceAnalysisService;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/query") // A new base path for query endpoints
public class PriceQueryController {

    private final PriceRepository priceRepository;
    private final PriceAnalysisService priceAnalysisService; // <-- INJECT NEW SERVICE

    public PriceQueryController(PriceRepository priceRepository, PriceAnalysisService priceAnalysisService) { // <-- UPDATE CONSTRUCTOR
        this.priceRepository = priceRepository;
        this.priceAnalysisService = priceAnalysisService;
    }

    // Endpoint to get all unique commodities available in the database
    @GetMapping("/commodities")
    public List<String> getDistinctCommodities() {
        return priceRepository.findDistinctCommodities();
    }

    // Endpoint to query prices by commodity, market, and date
    @GetMapping("/prices")
    public List<PriceRecord> getPrices(
            @RequestParam String commodity,
            @RequestParam String market,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scrapedDate) {
        System.out.println("Querying for: Commodity=" + commodity + ", Market=" + market + ", Date=" + scrapedDate);
        return priceRepository.findByCommodityIgnoreCaseAndMarketIgnoreCaseAndScrapedDate(commodity, market, scrapedDate);
    }

    // Endpoint to query prices by just commodity
    @GetMapping("/prices/byCommodity")
    public List<PriceRecord> getPricesByCommodity(@RequestParam String commodity) {
        System.out.println("Querying for: Commodity=" + commodity);
        return priceRepository.findByCommodityIgnoreCase(commodity);
    }

    // Optional: Get all records for demonstration (careful with large datasets!)
    @GetMapping("/prices/all")
    public List<PriceRecord> getAllPrices() {
        System.out.println("Fetching all price records.");
        return priceRepository.findAll();
    }
    @GetMapping("/analysis/commodity")
    public ResponseEntity<PriceAnalysisResult> getCommodityPriceAnalysis(
            @RequestParam String commodity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        System.out.println("Analyzing prices for commodity: " + commodity + " on date: " + date);
        Optional<PriceAnalysisResult> result = priceAnalysisService.analyzeCommodityPrices(commodity, date);

        return result.map(ResponseEntity::ok) // If result is present, return 200 OK with body
                .orElse(ResponseEntity.notFound().build()); // If not present, return 404 Not Found
    }
}