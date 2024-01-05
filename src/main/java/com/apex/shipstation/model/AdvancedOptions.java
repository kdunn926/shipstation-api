package com.apex.shipstation.model;

import lombok.Data;

@Data
public class AdvancedOptions {

    private Long warehouseId;
    private Boolean nonMachinable;
    private Boolean saturdayDelivery;
    private Boolean containsAlcohol;
    private Long storeId;
    private String customField1;
    private String customField2;
    private String customField3;
    private String source;
    private Boolean mergedOrSplit;
    private Long[] mergedIds;
    private String parentId;
    private String billToParty;
    private String billToAccount;
    private String billToPostalCode;
    private String billToCountryCode;
    private String billToMyOtherAccount;

}
