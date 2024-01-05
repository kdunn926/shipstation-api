package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Warehouse {

    private Long warehouseId;
    private String warehouseName;
    private Address originAddress;
    private Address returnAddress;
    private String createDate;
    private Boolean isDefault;

}
