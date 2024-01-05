package com.apex.shipstation.model;

import java.util.List;

import jakarta.persistence.Id;

import lombok.Data;

@Data
public class Order {

    public enum STATUS {
      pending_fulfillment,
      awaiting_payment,
      awaiting_shipment,
      shipped,
      on_hold,
      cancelled,
      processing
    };

    @Id
    private Long orderId;
    private String orderNumber;
    private String orderKey;
    private String orderDate;
    private String createDate;
    private String modifyDate;
    private String paymentDate;
    private String shipByDate;
    private STATUS orderStatus;
    private Long customerId;
    private String customerUsername;
    private String customerEmail;
    private Address billTo;
    private Address shipTo;
    private List<OrderItem> items;
    private Double orderTotal;
    private Double amountPaid;
    private Double taxAmount;
    private Double shippingAmount;
    private String customerNotes;
    private String internalNotes;
    private Boolean gift;
    private String giftMessage;
    private String paymentMethod;
    private String requestedShippingService;
    private String carrierCode;
    private String serviceCode;
    private String packageCode;
    private String confirmation;
    private String shipDate;
    private String holdUntilDate;
    private Weight weight;
    private Dimensions dimensions;
    private InsuranceOptions insuranceOptions;
    private InternationalOptions internationalOptions;
    private AdvancedOptions advancedOptions;
    private Long[] tagIds;
    private String userId;
    private Boolean externallyFulfilled;
    private String externallyFulfilledBy;
    private String externallyFulfilledById;
    private String externallyFulfilledByName;
    private String[] labelMessages;

}
