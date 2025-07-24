// File: src/main/java/com/agrimarket/priceanalysisservice/dto/ArbitrageAlert.java
package com.agrimarket.price_analysis_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ArbitrageAlert {
    private String commodity;
    private LocalDate alertDate;
    private BigDecimal minPrice;
    private String minPriceMarket;
    private String minPriceDistrict;
    private BigDecimal maxPrice;
    private String maxPriceMarket;
    private String maxPriceDistrict;
    private BigDecimal priceDifference;
    private String message;

    public ArbitrageAlert(String commodity, LocalDate alertDate, BigDecimal minPrice, String minPriceMarket, String minPriceDistrict, BigDecimal maxPrice, String maxPriceMarket, String maxPriceDistrict, BigDecimal priceDifference, String message) {
        this.commodity = commodity;
        this.alertDate = alertDate;
        this.minPrice = minPrice;
        this.minPriceMarket = minPriceMarket;
        this.minPriceDistrict = minPriceDistrict;
        this.maxPrice = maxPrice;
        this.maxPriceMarket = maxPriceMarket;
        this.maxPriceDistrict = maxPriceDistrict;
        this.priceDifference = priceDifference;
        this.message = message;
    }

    // --- Getters (add all of them for serialization) ---
    public String getCommodity() { return commodity; }
    public LocalDate getAlertDate() { return alertDate; }
    public BigDecimal getMinPrice() { return minPrice; }
    public String getMinPriceMarket() { return minPriceMarket; }
    public String getMinPriceDistrict() { return minPriceDistrict; }
    public BigDecimal getMaxPrice() { return maxPrice; }
    public String getMaxPriceMarket() { return maxPriceMarket; }
    public String getMaxPriceDistrict() { return maxPriceDistrict; }
    public BigDecimal getPriceDifference() { return priceDifference; }
    public String getMessage() { return message; }

    // --- Setters (optional for construction, but good practice for DTOs) ---
    public void setCommodity(String commodity) { this.commodity = commodity; }
    public void setAlertDate(LocalDate alertDate) { this.alertDate = alertDate; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public void setMinPriceMarket(String minPriceMarket) { this.minPriceMarket = minPriceMarket; }
    public void setMinPriceDistrict(String minPriceDistrict) { this.minPriceDistrict = minPriceDistrict; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    public void setMaxPriceMarket(String maxPriceMarket) { this.maxPriceMarket = maxPriceMarket; }
    public void setMaxPriceDistrict(String maxPriceDistrict) { this.maxPriceDistrict = maxPriceDistrict; }
    public void setPriceDifference(BigDecimal priceDifference) { this.priceDifference = priceDifference; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "ArbitrageAlert{" +
                "commodity='" + commodity + '\'' +
                ", alertDate=" + alertDate +
                ", minPrice=" + minPrice +
                ", minPriceMarket='" + minPriceMarket + '\'' +
                ", minPriceDistrict='" + minPriceDistrict + '\'' +
                ", maxPrice=" + maxPrice +
                ", maxPriceMarket='" + maxPriceMarket + '\'' +
                ", maxPriceDistrict='" + maxPriceDistrict + '\'' +
                ", priceDifference=" + priceDifference +
                ", message='" + message + '\'' +
                '}';
    }
}