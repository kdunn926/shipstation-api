package com.apex.shipstation.model;

import lombok.Data;

@Data
public class OrderResponse {

    private Long orderId;
    private String orderNumber;
    private String orderKey;
    private Boolean success;
    private String errorMessage;

}
