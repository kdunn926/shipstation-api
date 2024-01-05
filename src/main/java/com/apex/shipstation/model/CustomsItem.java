package com.apex.shipstation.model;

import lombok.Data;

@Data
public class CustomsItem {

    private String customsItemId;
    private String description;
    private Long quantity;
    private Double value;
    private String harmonizedTariffCode;
    private String countryOfOrigin;

}
