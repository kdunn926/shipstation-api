package com.apex.shipstation.model;

import lombok.Data;

@Data
public class StoreRefreshStatusResponse {

    private Long storeId;
    private Integer refreshStatusId;
    private String refreshStatus;
    private String lastRefreshAttempt;
    private String refreshDate;

}
