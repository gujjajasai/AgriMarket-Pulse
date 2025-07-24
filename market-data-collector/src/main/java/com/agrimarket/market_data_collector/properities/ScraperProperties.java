// File: src/main/java/com/agrimarket/marketdatacollector/properties/ScraperProperties.java
package com.agrimarket.market_data_collector.properities; // <-- FIXED: changed 'properities' to 'properties'

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "scraper")
public class ScraperProperties {
    private List<String> commodityIds;
    private List<String> stateCodes;
    private Map<String, String> districtStringsById;
    private Map<String, String> commodityNames;

    public List<String> getCommodityIds() { return commodityIds; }
    public void setCommodityIds(List<String> commodityIds) { this.commodityIds = commodityIds; }
    public List<String> getStateCodes() { return stateCodes; }
    public void setStateCodes(List<String> stateCodes) { this.stateCodes = stateCodes; }
    public Map<String, String> getDistrictStringsById() { return districtStringsById; }
    public void setDistrictStringsById(Map<String, String> districtStringsById) { this.districtStringsById = districtStringsById; }

    public Map<String, String> getCommodityNames() { return commodityNames; }
    public void setCommodityNames(Map<String, String> commodityNames) { this.commodityNames = commodityNames; }
}