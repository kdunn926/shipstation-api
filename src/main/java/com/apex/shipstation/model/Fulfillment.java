package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Fulfillment {

    private Long fulfillmentId;
    private Long orderId;
    private String orderNumber;
    private String userId;
    private String customerEmail;
    private String trackingNumber;
    private String createDate;
    private String shipDate;
    private String voidDate;
    private String deliveryDate;
    private String carrierCode;
    private String sellerFillProviderId;
    private String sellerFillProviderName;
    private String fulfillmentProviderCode;
    private String fulfillmentServiceCode;
    private Double fulfillmentFee;
    private Boolean voidRequested;
    private Boolean voided;
    private Boolean marketplaceNotified;
    private String notifyErrorMessage;
    private Address shipTo;

}
