package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class InternationalOptions {

    private String contents;
    private List<CustomsItem> customsItems;
    private String nonDelivery;

}
