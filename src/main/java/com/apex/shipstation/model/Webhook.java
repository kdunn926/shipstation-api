package com.apex.shipstation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Webhook {

    private Boolean labelApiHook;
    private Long webHookId;
    private Long sellerId;
    private Long storeId;
    private String hookType;
    private String messageFormat;
    private String url;
    private String name;
    private String bulkCopyBatchId;
    private String bulkCopyRecordId;
    private Boolean active;
    private List<String> webhookLogs;
    private String seller;
    private String store;

}
