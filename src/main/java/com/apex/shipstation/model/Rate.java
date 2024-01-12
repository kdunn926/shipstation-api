package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Rate {

    private String serviceName;
    private String serviceCode;
    private Double shipmentCost;
    private Double otherCost;

}
