package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Marketplace {

    private String name;
    private Long marketplaceId;
    private Boolean canRefresh;
    private Boolean supportsCustomMappings;
    private Boolean supportsCustomStatuses;
    private Boolean canConfirmShipments;

}
