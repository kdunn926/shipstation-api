package com.apex.shipstation.model;

import java.util.List;

import jakarta.persistence.Id;

import lombok.Data;

@Data
public class Shipment {

    @Id
    private Long shipmentId;
    private Long orderId;
    private String orderKey;
    private String userId;
    private String customerEmail;
    private String orderNumber;
    private String createDate;
    private String shipDate;
    private Double shipmentCost;
    private Double insuranceCost;
    private String trackingNumber;
    private Boolean returnLabel;
    private String batchNumber;
    private String carrierCode;
    private String serviceCode;
    private String packageCode;
    private String confirmation;
    private Long warehouseId;
    private Boolean voided;
    private String voidDate;
    private Boolean marketplaceNotified;
    private String notifyErrorMessage;
    private Address shipTo;
    private Weight weight;
    private Dimensions dimensions;
    private InsuranceOptions insuranceOptions;
    private AdvancedOptions advancedOptions;
    private List<OrderItem> shipmentItems;
    private String labelData;
    private String formData;

}
