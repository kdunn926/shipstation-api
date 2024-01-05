package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class OrderItem {

    private Long orderItemId;
    private String lineItemKey;
    private String sku;
    private String name;
    private String imageUrl;
    private Weight Weight;
    private Integer quantity;
    private Double unitPrice;
    private Double taxAmount;
    private Double shippingAmount;
    private String warehouseLocation;
    private List<ItemOption> options;
    private Long productId;
    private String fulfillmentSku;
    private Boolean adjustment;
    private String upc;
    private String createDate;
    private String modifyDate;

}
