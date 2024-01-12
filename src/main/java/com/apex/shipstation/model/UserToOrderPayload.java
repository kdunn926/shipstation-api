package com.apex.shipstation.model;

import lombok.Data;

@Data
public class UserToOrderPayload {

    private Long[] orderIds;
    private String userid;

}
