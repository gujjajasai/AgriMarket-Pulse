// File: src/main/java/com/agrimarket/marketdatacollector/controller/ScraperController.java
package com.agrimarket.market_data_collector.controller; // <-- FIXED: changed 'marketdatacollector' to 'market_data_collector'

import com.agrimarket.market_data_collector.model.MarketData; // Still needed for the return type, even if empty
import com.agrimarket.market_data_collector.service.AgmarknetScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
public class ScraperController {

    private final AgmarknetScraperService scraperService;

    public ScraperController(AgmarknetScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/scrape")
    public List<MarketData> startScraping() {
        System.out.println("Manual /scrape endpoint triggered. Initiating scheduled data collection.");
        scraperService.runScheduledScrape();
        return new ArrayList<>();
    }
}