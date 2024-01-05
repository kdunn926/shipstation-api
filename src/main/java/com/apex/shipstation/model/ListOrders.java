package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class ListOrders {

    private List<Order> orders;
    private Integer total;
    private Integer page;
    private Integer pages;

}
