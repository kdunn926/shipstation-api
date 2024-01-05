package com.apex.shipstation.model;

import lombok.Data;

@Data
public class ShipmentLabelPayload {

    private String carrierCode;
    private String serviceCode;
    private String packageCode;
    private String confirmation;
    private String shipDate;
    private Weight weight;
    private Dimensions dimensions;
    private Address shipFrom;
    private Address shipTo;
    private InsuranceOptions insuranceOptions;
    private InternationalOptions internationalOptions;
    private AdvancedOptions advancedOptions;
    private Boolean testLabel;

}
