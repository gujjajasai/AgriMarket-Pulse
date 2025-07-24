// File: src/main/java/com/agrimarket/marketdatacollector/model/MarketData.java
package com.agrimarket.market_data_collector.model;

public class MarketData {
    private String state; // We'll infer this or handle it as static for now (Maharashtra)
    private String district;
    private String market;
    private String commodity; // This will actually be the variety (e.g., "Other", "Red")
    private String minPrice;
    private String maxPrice;
    private String modalPrice;
    private String date; // <-- Date field is back!

    public MarketData() {
    }

    // Manual all-args constructor (8 arguments now)
    public MarketData(String state, String district, String market, String commodity,
                      String minPrice, String maxPrice, String modalPrice, String date) {
        this.state = state;
        this.district = district;
        this.market = market;
        this.commodity = commodity;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.modalPrice = modalPrice;
        this.date = date;
    }

    // --- Explicit Getters and Setters (ensure these are updated to match fields) ---

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getMarket() { return market; }
    public void setMarket(String market) { this.market = market; }

    public String getCommodity() { return commodity; } // This will contain variety, like "Other"
    public void setCommodity(String commodity) { this.commodity = commodity; }

    public String getMinPrice() { return minPrice; }
    public void setMinPrice(String minPrice) { this.minPrice = minPrice; }

    public String getMaxPrice() { return maxPrice; }
    public void setMaxPrice(String maxPrice) { this.maxPrice = maxPrice; }

    public String getModalPrice() { return modalPrice; }
    public void setModalPrice(String modalPrice) { this.modalPrice = modalPrice; }

    public String getDate() { return date; } // <-- New Getter/Setter for Date
    public void setDate(String date) { this.date = date; }
}