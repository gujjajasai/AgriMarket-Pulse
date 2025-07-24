// File: src/main/java/com/agrimarket/priceanalysisservice/entity/PriceRecord.java
package com.agrimarket.price_analysis_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "price_records", uniqueConstraints = { // <-- ADD uniqueConstraints HERE
        @UniqueConstraint(columnNames = {"scrapedDate", "commodity", "market", "district", "state"})
})
public class PriceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;
    private String district;
    private String market;
    private String commodity;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal modalPrice;

    private LocalDate scrapedDate;

    // ... (keep all explicit getters/setters and constructor as they are) ...
 // This is the correct field

    // --- Manually added No-Argument Constructor (Required by JPA) ---
    public PriceRecord() {
    }

    // --- Manually added Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getModalPrice() {
        return modalPrice;
    }

    public void setModalPrice(BigDecimal modalPrice) {
        this.modalPrice = modalPrice;
    }

    // --- Correct Getters and Setters for 'scrapedDate' ---
    public LocalDate getScrapedDate() {
        return scrapedDate;
    }

    public void setScrapedDate(LocalDate scrapedDate) {
        this.scrapedDate = scrapedDate;
    }
}