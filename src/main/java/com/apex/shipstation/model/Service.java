package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Service {

    private String carrierCode;
    private String code;
    private String name;
    private Boolean domestic;
    private Boolean international;

}
