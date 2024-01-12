package com.apex.shipstation.model;

import lombok.Data;

@Data
public class InsuranceOptions {

    private String provider;
    private Boolean insureShipment;
    private Double insuredValue;

}
