package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class Store {

    private Long storeId;
    private String storeName;
    private Long marketplaceId;
    private String marketplaceName;
    private String accountName;
    private String email;
    private String integrationUrl;
    private Boolean active;
    private String companyName;
    private String phone;
    private String publicEmail;
    private String website;
    private String refreshDate;
    private String lastRefreshAttempt;
    private String createDate;
    private String modifyDate;
    private Boolean autoRefresh;
    private List<StatusMapping> statusMappings;

}
