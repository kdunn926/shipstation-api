package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class BulkOrdersResponse {

    private Boolean hasErrors;
    private List<OrderResponse> results;
    private String message;

}
