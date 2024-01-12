package com.apex.shipstation.model;

import lombok.Data;

@Data
public class RatePayload {

    private String carrierCode;
    private String serviceCode;
    private String packageCode;
    private String fromPostalCode;
    private String toState;
    private String toCountry;
    private String toPostalCode;
    private String toCity;
    private Weight weight;
    private Dimensions dimensions;
    private String confirmation;
    private Boolean residential;

}
