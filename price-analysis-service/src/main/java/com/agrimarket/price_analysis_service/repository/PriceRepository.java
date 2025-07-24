// File: src/main/java/com/agrimarket/priceanalysisservice/repository/PriceRepository.java
package com.agrimarket.price_analysis_service.repository;

import com.agrimarket.price_analysis_service.entity.PriceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;// <-- ADD THIS IMPORT

@Repository
public interface PriceRepository extends JpaRepository<PriceRecord, Long> {

    // Spring Data JPA will automatically implement this method
    // It will find PriceRecords where commodity matches (case-insensitive) AND market matches (case-insensitive) AND date matches
    List<PriceRecord> findByCommodityIgnoreCaseAndMarketIgnoreCaseAndScrapedDate(String commodity, String market, LocalDate recordDate);

    // Let's add another useful one: find by commodity, case-insensitive
    List<PriceRecord> findByCommodityIgnoreCase(String commodity);

    // Find all unique commodities
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT p.commodity FROM PriceRecord p")
    List<String> findDistinctCommodities();

    @Query("SELECT DISTINCT p.commodity FROM PriceRecord p WHERE p.scrapedDate = :date")
    List<String> findDistinctCommoditiesByScrapedDate(LocalDate date);

    List<PriceRecord> findByCommodityIgnoreCaseAndScrapedDate(String commodity, LocalDate scrapedDate);
}