package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Label {

    private Long shipmentId;
    private Double shipmentCost;
    private Double insuranceCost;
    private String trackingNumber;
    private String labelData;
    private String formData;

}
