// File: src/main/java/com/agrimarket/market_data_collector/service/AgmarknetScraperService.java
package com.agrimarket.market_data_collector.service; // Use your actual package, e.g., com.agrimarket.market_data_collector.service

import com.agrimarket.market_data_collector.model.MarketData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agrimarket.market_data_collector.properities.ScraperProperties; // <-- FIXED: changed 'properities' to 'properties'

@Service
public class AgmarknetScraperService {

    private static final String BASE_URL_TEMPLATE = "https://agmarknet.gov.in/SearchCmmMkt.aspx?Tx_Commodity=%s&Tx_State=%s&Tx_District=%s&DateFrom=%s&DateTo=%s&Fr_Date=%s&To_Date=%s&Tx_Trend=0&Tx_CommodityHead=%s&Tx_StateHead=None&Tx_DistrictHead=None&Tx_MarketHead=--Select--";
    private static final DateTimeFormatter URL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private final RestTemplate restTemplate;
    private final ScraperProperties scraperProperties;

    @Value("${analysis.service.url}")
    private String analysisServiceUrl;

    public AgmarknetScraperService(RestTemplate restTemplate, ScraperProperties scraperProperties) {
        this.restTemplate = restTemplate;
        this.scraperProperties = scraperProperties;
    }

    //@Scheduled(cron = "0 * * * * *") //
    public void runScheduledScrape() {
        System.out.println("-------------------------------------------");
        System.out.println("Scheduled task started: Fetching market data dynamically...");
        List<MarketData> allScrapedData = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String dateString = today.format(URL_DATE_FORMATTER);

        for (String commodityId : scraperProperties.getCommodityIds()) {
            String commodityName = scraperProperties.getCommodityNames().getOrDefault(commodityId, "Unknown");
            if ("Unknown".equals(commodityName)) {
                System.err.println("Warning: No commodity name found for ID: " + commodityId + ". Skipping this commodity.");
                continue;
            }

            for (String stateCode : scraperProperties.getStateCodes()) {
                String districtIdsString = scraperProperties.getDistrictStringsById().get(stateCode);
                List<String> districts = new ArrayList<>();
                if (districtIdsString != null && !districtIdsString.trim().isEmpty()) {
                    districts = List.of(districtIdsString.split(","));
                }

                if (districts.isEmpty()) {
                    System.out.println("No districts configured for state: " + stateCode);
                    continue;
                }
                for (String districtId : districts) {
                    String cleanedDistrictId = districtId.trim();

                    String dynamicUrl = String.format(BASE_URL_TEMPLATE,
                            commodityId, stateCode, cleanedDistrictId,
                            dateString, dateString, dateString, dateString,
                            commodityName
                    );

                    System.out.println("Scraping from: " + dynamicUrl);
                    List<MarketData> scrapedData = this.scrapeData(dynamicUrl, stateCode, commodityId);
                    if (scrapedData != null && !scrapedData.isEmpty()) {
                        allScrapedData.addAll(scrapedData);
                    }
                }
            }
        }

        if (!allScrapedData.isEmpty()) {
            System.out.println("Successfully scraped " + allScrapedData.size() + " total records. Sending to analysis service...");
            try {
                restTemplate.postForEntity(analysisServiceUrl, allScrapedData, String.class);
                System.out.println("Data sent successfully to " + analysisServiceUrl);
            } catch (Exception e) {
                System.err.println("Error sending data to analysis service: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No data was scraped in this run from any source. Nothing to send.");
        }
        System.out.println("Scheduled task finished.");
        System.out.println("-------------------------------------------");
    }

    public List<MarketData> scrapeData(String url, String stateCode, String commodityId) {
        List<MarketData> marketDataList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(30000)
                    .get();

            Elements tableRows = doc.select("table.tableagmark_new tr");

            for (int i = 1; i < tableRows.size(); i++) {
                Element row = tableRows.get(i);
                Elements cols = row.select("td");

                if (cols.size() == 10) {
                    MarketData data = new MarketData(
                            getActualStateName(stateCode),
                            cols.get(1).text(),
                            cols.get(2).text(),
                            cols.get(4).text(), // Commodity/Variety from table
                            cols.get(6).text(), // Min Price
                            cols.get(7).text(), // Max Price
                            cols.get(8).text(), // Modal Price
                            cols.get(9).text()  // Date
                    );
                    marketDataList.add(data);
                } else {
                    System.err.println("Skipping row " + i + ": Expected 10 columns, but found " + cols.size() + " from URL: " + url);
                }
            }
        } catch (IOException e) {
            System.err.println("Error scraping " + url + ": " + e.getMessage());
        }
        return marketDataList;
    }

    private String getActualStateName(String stateCode) {
        return switch (stateCode) {
            case "MH" -> "Maharashtra";
            case "UP" -> "Uttar Pradesh";
            default -> stateCode;
        };
    }
}