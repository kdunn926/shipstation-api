package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class ListCustomers {

    private List<Customer> customers;
    private Integer total;
    private Integer page;
    private Integer pages;

}
