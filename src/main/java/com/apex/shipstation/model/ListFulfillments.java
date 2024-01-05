package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class ListFulfillments {

    private List<Fulfillment> fulfillments;
    private Integer total;
    private Integer page;
    private Integer pages;

}
