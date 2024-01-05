package com.apex.shipstation.model;

import lombok.Data;

@Data
public class MarketplaceUsername {

    private Long customerUserId;
    private Long customerId;
    private String createDate;
    private String modifyDate;
    private Integer marketplaceId;
    private String marketplace;
    private String username;

}
