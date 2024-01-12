package com.apex.shipstation.model;

import lombok.Data;

@Data
public class OrderAsShippedResponse {

    private Long orderId;
    private String orderNumber;

}
