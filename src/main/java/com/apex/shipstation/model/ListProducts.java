package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class ListProducts {

    private List<Product> products;
    private Integer total;
    private Integer page;
    private Integer pages;

}
