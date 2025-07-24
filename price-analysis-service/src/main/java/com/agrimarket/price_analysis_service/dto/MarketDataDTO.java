// File: src/main/java/com/agrimarket/priceanalysisservice/dto/MarketDataDTO.java
// File: src/main/java/com/agrimarket/priceanalysisservice/dto/MarketDataDTO.java
package com.agrimarket.price_analysis_service.dto; // Or your package with underscores

import lombok.Data; // Keep for toString(), equals(), hashCode()

@Data
public class MarketDataDTO {
    private String state;
    private String district;
    private String market;
    private String commodity;
    private String minPrice;
    private String maxPrice;
    private String modalPrice;
    private String date;

    // --- Manually added Getters and Setters ---
    // (Lombok would generate these, but we're adding them explicitly for robustness)

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getModalPrice() {
        return modalPrice;
    }

    public void setModalPrice(String modalPrice) {
        this.modalPrice = modalPrice;
    }
}