package com.apex.shipstation.model;

import lombok.Data;

@Data
public class LabelForOrderPayload {

    private Long orderId;
    private String carrierCode;
    private String serviceCode;
    private String confirmation;
    private String shipDate;
    private Weight weight;
    private Dimensions dimensions;
    private InsuranceOptions insuranceOptions;
    private InternationalOptions internationalOptions;
    private AdvancedOptions advancedOptions;
    private Boolean testLabel;

}
