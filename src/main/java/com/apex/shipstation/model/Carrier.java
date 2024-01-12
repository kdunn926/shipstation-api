package com.apex.shipstation.model;

import lombok.Data;

@Data
public class Carrier {

    private String name;
    private String nickname;
    private String code;
    private String accountNumber;
    private Boolean requiresFundedAccount;
    private Float balance;
    private Integer shippingProviderId;
    private Boolean primary;

}
