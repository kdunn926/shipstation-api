package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class ListShipments {

    private List<Shipment> shipments;
    private Integer total;
    private Integer page;
    private Integer pages;

}

