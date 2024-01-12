package com.apex.shipstation.model;

import lombok.Data;

@Data
public class SubscribeWebhookPayload {

    private String target_url;
    private String event;
    private Long storeId;
    private String friendlyName;

}
