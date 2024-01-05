package com.apex.shipstation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Webhook {

    private Boolean IsLabelAPIHook;
    private Long WebHookID;
    private Long SellerID;
    private Long StoreID;
    private String HookType;
    private String MessageFormat;
    private String Url;
    private String Name;
    private String BulkCopyBatchID;
    private String BulkCopyRecordID;
    private Boolean Active;
    private List<String> WebhookLogs;
    private String Seller;
    private String Store;

}
