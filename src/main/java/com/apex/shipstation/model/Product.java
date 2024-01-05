package com.apex.shipstation.model;

import java.util.List;

import lombok.Data;

@Data
public class Product {

    private Long productId;
    private String aliases;
    private String sku;
    private String name;
    private Double price;
    private Double defaultCost;
    private Double length;
    private Double width;
    private Double height;
    private Double weightOz;
    private String internalNotes;
    private String fulfillmentSku;
    private String createDate;
    private String modifyDate;
    private Boolean active;
    private ProductCategory ProductCategory;
    private String productType;
    private String warehouseLocation;
    private String defaultCarrierCode;
    private String defaultServiceCode;
    private String defaultPackageCode;
    private String defaultIntlCarrierCode;
    private String defaultIntlServiceCode;
    private String defaultIntlPackageCode;
    private String defaultConfirmation;
    private String defaultIntlConfirmation;
    private String customsDescription;
    private Double customsValue;
    private String customsTariffNo;
    private String customsCountryCode;
    private Boolean noCustoms;
    private List<ProductTag> tags;

}
