package com.apex.shipstation.model;

import lombok.Data;

@Data
public class OrderAsShippedPayload {

    private Long orderId;
    private String carrierCode;
    private String shipDate;
    private String trackingNumber;
    private Boolean notifyCustomer;
    private Boolean notifySalesChannel;

}
