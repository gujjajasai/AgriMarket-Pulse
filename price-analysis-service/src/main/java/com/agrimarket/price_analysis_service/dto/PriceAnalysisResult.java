// File: src/main/java/com/agrimarket/priceanalysisservice/dto/PriceAnalysisResult.java
package com.agrimarket.price_analysis_service.dto;

import java.math.BigDecimal;

public class PriceAnalysisResult {
    private String commodity;
    private String scrapedDate; // Keeping as String for consistency with DTO received from collector
    private BigDecimal overallMinPrice;
    private String minPriceMarket;
    private BigDecimal overallMaxPrice;
    private String maxPriceMarket;
    private BigDecimal priceDifference;

    // Constructor with all fields
    public PriceAnalysisResult(String commodity, String scrapedDate, BigDecimal overallMinPrice, String minPriceMarket, BigDecimal overallMaxPrice, String maxPriceMarket, BigDecimal priceDifference) {
        this.commodity = commodity;
        this.scrapedDate = scrapedDate;
        this.overallMinPrice = overallMinPrice;
        this.minPriceMarket = minPriceMarket;
        this.overallMaxPrice = overallMaxPrice;
        this.maxPriceMarket = maxPriceMarket;
        this.priceDifference = priceDifference;
    }

    // --- Getters (add all of them) ---
    public String getCommodity() { return commodity; }
    public String getScrapedDate() { return scrapedDate; }
    public BigDecimal getOverallMinPrice() { return overallMinPrice; }
    public String getMinPriceMarket() { return minPriceMarket; }
    public BigDecimal getOverallMaxPrice() { return overallMaxPrice; }
    public String getMaxPriceMarket() { return maxPriceMarket; }
    public BigDecimal getPriceDifference() { return priceDifference; }

    // --- Setters (add all of them, though they might not be directly used for construction) ---
    public void setCommodity(String commodity) { this.commodity = commodity; }
    public void setScrapedDate(String scrapedDate) { this.scrapedDate = scrapedDate; }
    public void setOverallMinPrice(BigDecimal overallMinPrice) { this.overallMinPrice = overallMinPrice; }
    public void setMinPriceMarket(String minPriceMarket) { this.minPriceMarket = minPriceMarket; }
    public void setOverallMaxPrice(BigDecimal overallMaxPrice) { this.overallMaxPrice = overallMaxPrice; }
    public void setMaxPriceMarket(String maxPriceMarket) { this.maxPriceMarket = maxPriceMarket; }
    public void setPriceDifference(BigDecimal priceDifference) { this.priceDifference = priceDifference; }

    @Override
    public String toString() {
        return "PriceAnalysisResult{" +
                "commodity='" + commodity + '\'' +
                ", scrapedDate='" + scrapedDate + '\'' +
                ", overallMinPrice=" + overallMinPrice +
                ", minPriceMarket='" + minPriceMarket + '\'' +
                ", overallMaxPrice=" + overallMaxPrice +
                ", maxPriceMarket='" + maxPriceMarket + '\'' +
                ", priceDifference=" + priceDifference +
                '}';
    }
}