package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class Customer {

    private Long customerId;
    private String createDate;
    private String modifyDate;
    private String name;
    private String company;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;
    private String phone;
    private String email;
    private String addressVerified;
    private List<MarketplaceUsername> marketplaceUsernames;
    private List<Tag> tags;

}
