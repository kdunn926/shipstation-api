package com.apex.shipstation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.apex.shipstation.model.*;

public class APITest {
    private static API api;

    @BeforeAll
    public static void initAPI() {
        api = new API("https://private-anon-87b8c11456-shipstation.apiary-mock.com", "key", "secret");
    }

    @Test
    public void testListTags() throws IOException, InterruptedException {

        List<Tag> carriers = api.listTags();

        assertThat( 8362).isEqualTo(carriers.get(0).getTagId());
        assertThat( "Backorder").isEqualTo(carriers.get(0).getName());
        assertThat( "#800080").isEqualTo(carriers.get(0).getColor());

        assertThat( 8324).isEqualTo(carriers.get(1).getTagId());
        assertThat( "Canada").isEqualTo(carriers.get(1).getName());
        assertThat( "#ff0000").isEqualTo(carriers.get(1).getColor());

    }

    @Test
    public void testListCarriers() throws IOException, InterruptedException {

        List<Carrier> carriers = api.listCarriers();

        assertThat( "Stamps.com").isEqualTo(carriers.get(0).getName());
        assertThat( "stamps_com").isEqualTo(carriers.get(0).getCode());
        assertThat( "SS123").isEqualTo(carriers.get(0).getAccountNumber());
        assertThat( true).isEqualTo(carriers.get(0).getRequiresFundedAccount());
        assertThat( 0.01).isEqualTo(carriers.get(0).getBalance());

    }

    @Test
    public void testGetCarrier() throws IOException, InterruptedException {

        Carrier carrier = api.getCarrier("stamps_com");

        assertThat( "Stamps.com").isEqualTo(carrier.getName());
        assertThat( "stamps_com").isEqualTo(carrier.getCode());
        assertThat( "SS123").isEqualTo(carrier.getAccountNumber());
        assertThat( true).isEqualTo(carrier.getRequiresFundedAccount());
        assertThat( 0.01).isEqualTo(carrier.getBalance());
    }

    @Test
    public void testAddFund() throws IOException, InterruptedException {

        Fund fund = new Fund();
        fund.setCarrierCode("fedex");
        fund.setAmount(20.0);

        Carrier carrier = api.addFunds(fund);

        assertThat( "Stamps.com").isEqualTo(carrier.getName());
        assertThat( "stamps_com").isEqualTo(carrier.getCode());
        assertThat( "SS123").isEqualTo(carrier.getAccountNumber());
        assertThat( true).isEqualTo(carrier.getRequiresFundedAccount());
        assertThat( 0.01).isEqualTo(carrier.getBalance());

    }

    @Test
    public void testListPackages() throws IOException, InterruptedException {

        List<com.apex.shipstation.model.Package> packages = api.listPackages("express_1");

        assertThat( "express_1").isEqualTo(packages.get(0).getCarrierCode());
        assertThat( "cubic").isEqualTo(packages.get(0).getCode());
        assertThat( "Cubic").isEqualTo(packages.get(0).getName());
        assertThat( true).isEqualTo(packages.get(0).getDomestic());
        assertThat( false).isEqualTo(packages.get(0).getInternational());

        assertThat( "express_1").isEqualTo(packages.get(1).getCarrierCode());
        assertThat( "dvd_flat_rate_box").isEqualTo(packages.get(1).getCode());
        assertThat( "DVD Flat Rate Box").isEqualTo(packages.get(1).getName());
        assertThat( false).isEqualTo(packages.get(1).getDomestic());
        assertThat( true).isEqualTo(packages.get(1).getInternational());

    }

    @Test
    public void testListServices() throws IOException, InterruptedException {

        List<Service> services = api.listServices("fedex");

        assertThat( "fedex").isEqualTo(services.get(0).getCarrierCode());
        assertThat( "fedex_ground").isEqualTo(services.get(0).getCode());
        assertThat( "FedEx Ground" + Character.toString((char)174)).isEqualTo(services.get(0).getName());
        assertThat( true).isEqualTo(services.get(0).getDomestic());
        assertThat( false).isEqualTo(services.get(0).getInternational());
    }

    @Test
    public void testGetCustomer() throws IOException, InterruptedException {

        Customer customer = api.getCustomer("12345678");

        assertThat( 12345678).isEqualTo(customer.getCustomerId());
        assertThat( "2014-11-18T10:33:01.1900000").isEqualTo(customer.getCreateDate());
        assertThat( "2014-11-18T10:33:01.1900000").isEqualTo(customer.getModifyDate());
        assertThat( "Cam Newton").isEqualTo(customer.getName());
        assertThat( "Test Company").isEqualTo(customer.getCompany());
        assertThat( "123 War Eagle Lane").isEqualTo(customer.getStreet1());
        assertThat( "").isEqualTo(customer.getStreet2());
        assertThat( "Auburn").isEqualTo(customer.getCity());
        assertThat( "AL").isEqualTo(customer.getState());

        assertThat( "36830").isEqualTo(customer.getPostalCode());
        assertThat( "US").isEqualTo(customer.getCountryCode());
        assertThat( "555-555-5555").isEqualTo(customer.getPhone());
        assertThat( "supermancam@example.com").isEqualTo(customer.getEmail());
        assertThat( "Verified").isEqualTo(customer.getAddressVerified());

        List<MarketplaceUsername> marketplaceUsernames = customer.getMarketplaceUsernames();

        assertThat( 67195020).isEqualTo(marketplaceUsernames.get(0).getCustomerUserId());
        assertThat( 12345678).isEqualTo(marketplaceUsernames.get(0).getCustomerId());
        assertThat( "2015-04-27T12:35:03.8300000").isEqualTo(marketplaceUsernames.get(0).getCreateDate());
        assertThat( "2015-05-14T08:16:15.2700000").isEqualTo(marketplaceUsernames.get(0).getModifyDate());
        assertThat( 0).isEqualTo(marketplaceUsernames.get(0).getMarketplaceId());
        assertThat( "ShipStation").isEqualTo(marketplaceUsernames.get(0).getMarketplace());
        assertThat( "camtheman@gmail.com").isEqualTo(marketplaceUsernames.get(0).getUsername());

        List<Tag> tags = customer.getTags();
        assertThat( 1234).isEqualTo(tags.get(0).getTagId());
        assertThat( "Expedited").isEqualTo(tags.get(0).getName());
        assertThat( 9725).isEqualTo(tags.get(1).getTagId());
        assertThat( "00 BULK ORDERED").isEqualTo(tags.get(1).getName());

    }

    @Test
    public void testListCustomers() throws IOException, InterruptedException {

        ListCustomers listCustomer = api.listCustomers();

        Customer customer = listCustomer.getCustomers().get(0);

        assertThat( 12345678).isEqualTo(customer.getCustomerId());
        assertThat( "2014-11-18T10:33:01.1900000").isEqualTo(customer.getCreateDate());
        assertThat( "2014-11-18T10:33:01.1900000").isEqualTo(customer.getModifyDate());
        assertThat( "Cam Newton").isEqualTo(customer.getName());
        assertThat( "Test Company").isEqualTo(customer.getCompany());
        assertThat( "123 War Eagle Lane").isEqualTo(customer.getStreet1());
        assertThat( "").isEqualTo(customer.getStreet2());
        assertThat( "Auburn").isEqualTo(customer.getCity());
        assertThat( "AL").isEqualTo(customer.getState());

        assertThat( "36830").isEqualTo(customer.getPostalCode());
        assertThat( "US").isEqualTo(customer.getCountryCode());
        assertThat( "555-555-5555").isEqualTo(customer.getPhone());
        assertThat( "supermancam@example.com").isEqualTo(customer.getEmail());
        assertThat( "Verified").isEqualTo(customer.getAddressVerified());

        List<MarketplaceUsername> marketplaceUsernames = customer.getMarketplaceUsernames();

        assertThat( 67195020).isEqualTo(marketplaceUsernames.get(0).getCustomerUserId());
        assertThat( 12345678).isEqualTo(marketplaceUsernames.get(0).getCustomerId());
        assertThat( "2015-04-27T12:35:03.8300000").isEqualTo(marketplaceUsernames.get(0).getCreateDate());
        assertThat( "2015-05-14T08:16:15.2700000").isEqualTo(marketplaceUsernames.get(0).getModifyDate());
        assertThat( 0).isEqualTo(marketplaceUsernames.get(0).getMarketplaceId());
        assertThat( "ShipStation").isEqualTo(marketplaceUsernames.get(0).getMarketplace());
        assertThat( "camtheman@gmail.com").isEqualTo(marketplaceUsernames.get(0).getUsername());

        List<Tag> tags = customer.getTags();
        assertThat( 1234).isEqualTo(tags.get(0).getTagId());
        assertThat( "Expedited").isEqualTo(tags.get(0).getName());
        assertThat( 9725).isEqualTo(tags.get(1).getTagId());
        assertThat( "00 BULK ORDERED").isEqualTo(tags.get(1).getName());

        assertThat( 2).isEqualTo(listCustomer.getTotal());
        assertThat( 1).isEqualTo(listCustomer.getPage());
        assertThat( 1).isEqualTo(listCustomer.getPages());
    }

    @Test
    public void testListFulfillments() throws IOException, InterruptedException {

        ListFulfillments fulfillments = api.listFulfillments();

        assertThat( 33974374).isEqualTo(fulfillments.getFulfillments().get(0).getFulfillmentId());
        assertThat( 191759016L).isEqualTo(fulfillments.getFulfillments().get(0).getOrderId());
        assertThat( "101").isEqualTo(fulfillments.getFulfillments().get(0).getOrderNumber());
        assertThat( "c9f06d74-95de-4263-9b04-e87095cababf").isEqualTo(fulfillments.getFulfillments().get(0).getUserId());
        assertThat( "apisupport@shipstation.com").isEqualTo(fulfillments.getFulfillments().get(0).getCustomerEmail());
        assertThat( "783408231234").isEqualTo(fulfillments.getFulfillments().get(0).getTrackingNumber());
        assertThat( "2016-06-07T08:50:50.0670000").isEqualTo(fulfillments.getFulfillments().get(0).getCreateDate());
        assertThat( "2016-06-07T00:00:00.0000000").isEqualTo(fulfillments.getFulfillments().get(0).getShipDate());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getVoidDate());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getDeliveryDate());
        assertThat( "USPS").isEqualTo(fulfillments.getFulfillments().get(0).getCarrierCode());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getFulfillmentProviderCode());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getFulfillmentServiceCode());
        assertThat( 0.01).isEqualTo(fulfillments.getFulfillments().get(0).getFulfillmentFee());
        assertThat( false).isEqualTo(fulfillments.getFulfillments().get(0).getVoidRequested());
        assertThat( false).isEqualTo(fulfillments.getFulfillments().get(0).getVoided());
        assertThat( true).isEqualTo(fulfillments.getFulfillments().get(0).getMarketplaceNotified());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getNotifyErrorMessage());

        assertThat( "Yoda").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getName());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getCompany());
        assertThat( "3800 N Lamar Blvd # 220").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getStreet1());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getStreet2());
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getStreet3());
        assertThat( "AUSTIN").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getCity());
        assertThat( "TX").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getState());
        assertThat( "78756").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getPostalCode());
        assertThat( "US").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getCountry());

        assertThat( "512-485-4282").isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getPhone());
        assertThat( false).isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getResidential()); //null boolean is a false
        //assertThat( null).isEqualTo(fulfillments.getFulfillments().get(0).getShipTo().getAddressVerified());

    }

    @Test
    public void testGetOrder() throws IOException, InterruptedException {

        Order order = api.getOrder(123456789);

        assertThat( 94113592L).isEqualTo(order.getOrderId());
        assertThat( "TEST-ORDER-API-DOCS").isEqualTo(order.getOrderNumber());
        assertThat( "0f6bec18-9-4771-83aa-f392d84f4c74").isEqualTo(order.getOrderKey());
        assertThat( "2015-06-29T08:46:27.0000000").isEqualTo(order.getOrderDate());
        assertThat( "2015-07-16T14:00:34.8230000").isEqualTo(order.getCreateDate());
        assertThat( "2015-09-08T11:03:12.3800000").isEqualTo(order.getModifyDate());
        assertThat( "2015-06-29T08:46:27.0000000").isEqualTo(order.getPaymentDate());
        assertThat( "2015-07-05T00:00:00.0000000").isEqualTo(order.getShipByDate());
        assertThat( Order.STATUS.awaiting_shipment).isEqualTo(order.getOrderStatus());
        assertThat( 37701499).isEqualTo(order.getCustomerId());
        assertThat( "headhoncho@whitehouse.gov").isEqualTo(order.getCustomerUsername());
        assertThat( "The President").isEqualTo(order.getBillTo().getName());
        //assertThat( null).isEqualTo(order.getBillTo().getCompany());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet1());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet2());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet3());
        //assertThat( null).isEqualTo(order.getBillTo().getCity());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getPostalCode());
        //assertThat( null).isEqualTo(order.getBillTo().getPhone());
        assertThat( false).isEqualTo(order.getBillTo().getResidential());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getAddressVerified());

        assertThat( "The President").isEqualTo(order.getShipTo().getName());
        assertThat( "US Govt").isEqualTo(order.getShipTo().getCompany());
        assertThat( "1600 Pennsylvania Ave").isEqualTo(order.getShipTo().getStreet1());
        assertThat( "Oval Office").isEqualTo(order.getShipTo().getStreet2());
        //assertThat( null).isEqualTo(order.getShipTo().getStreet3());
        assertThat( "Washington").isEqualTo(order.getShipTo().getCity());
        assertThat( "DC").isEqualTo(order.getShipTo().getState());
        assertThat( "20500").isEqualTo(order.getShipTo().getPostalCode());
        assertThat( "555-555-5555").isEqualTo(order.getShipTo().getPhone());
        assertThat( false).isEqualTo(order.getShipTo().getResidential());
        assertThat( "DC").isEqualTo(order.getShipTo().getState());
        assertThat( "Address validation warning").isEqualTo(order.getShipTo().getAddressVerified());

        List<OrderItem> items = order.getItems();

        assertThat( 128836912).isEqualTo(items.get(0).getOrderItemId());
        assertThat( "vd08-MSLbtx").isEqualTo(items.get(0).getLineItemKey());
        assertThat( "ABC123").isEqualTo(items.get(0).getSku());
        assertThat( "Test item #1").isEqualTo(items.get(0).getName());
        //assertThat( null).isEqualTo(items.get(0).getImageUrl());
        assertThat( 24).isEqualTo(items.get(0).getWeight().getValue());
        assertThat( "ounces").isEqualTo(items.get(0).getWeight().getUnits());
        assertThat( 0.01).isEqualTo(items.get(0).getQuantity());
        assertThat( 0.01).isEqualTo(items.get(0).getUnitPrice());
        assertThat( 0.01).isEqualTo(items.get(0).getTaxAmount()); //null double is 0.0
        assertThat( 0.01).isEqualTo(items.get(0).getShippingAmount());//null double is 0.0
        assertThat( "Bin 7").isEqualTo(items.get(0).getWarehouseLocation());
        assertThat( "Size").isEqualTo(items.get(0).getOptions().get(0).getName());
        assertThat( "Large").isEqualTo(items.get(0).getOptions().get(0).getValue());
        assertThat( 0.01).isEqualTo(order.getOrderTotal());
        assertThat( 0.01).isEqualTo(order.getAmountPaid());
        assertThat( 0.01).isEqualTo(order.getTaxAmount());
        assertThat( 0.01).isEqualTo(order.getShippingAmount());
        assertThat( "Please ship as soon as possible!").isEqualTo(order.getCustomerNotes());
        assertThat( "Customer called and would like to upgrade shipping").isEqualTo(order.getInternalNotes());
        assertThat( true).isEqualTo(order.getGift());
        assertThat( "Thank you!").isEqualTo(order.getGiftMessage());
        assertThat( "Credit Card").isEqualTo(order.getPaymentMethod());
        assertThat( "Priority Mail").isEqualTo(order.getRequestedShippingService());
        assertThat( "fedex").isEqualTo(order.getCarrierCode());
        assertThat( "fedex_home_delivery").isEqualTo(order.getServiceCode());
        assertThat( "package").isEqualTo(order.getPackageCode());
        assertThat( "delivery").isEqualTo(order.getConfirmation());
        assertThat( "2015-07-02").isEqualTo(order.getShipDate());
        //assertThat( null).isEqualTo(order.getHoldUntilDate());
        assertThat( 48).isEqualTo(order.getWeight().getValue());
        assertThat( "ounces").isEqualTo(order.getWeight().getUnits());
        assertThat( "inches").isEqualTo(order.getDimensions().getUnits());
        assertThat( 0.1).isEqualTo(order.getDimensions().getLength());
        assertThat( 0.1).isEqualTo(order.getDimensions().getWidth());
        assertThat( 0.1).isEqualTo(order.getDimensions().getHeight());
        assertThat( "carrier").isEqualTo(order.getInsuranceOptions().getProvider());
        assertThat( true).isEqualTo(order.getInsuranceOptions().getInsureShipment());
        assertThat( 0.01).isEqualTo(order.getInsuranceOptions().getInsuredValue());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getContents());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getCustomsItems());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getNonDelivery());
        assertThat( 24079).isEqualTo(order.getAdvancedOptions().getWarehouseId());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getNonMachinable());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getSaturdayDelivery());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getContainsAlcohol());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getMergedOrSplit());
        assertThat( 0).isEqualTo(order.getAdvancedOptions().getMergedIds().length); //checking length, cant compare Long[]
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getParentId());
        assertThat( 26815).isEqualTo(order.getAdvancedOptions().getStoreId());
        assertThat( "Custom data that you can add to an order. See Custom Field #2 & #3 for more info!").isEqualTo(order.getAdvancedOptions().getCustomField1());
        assertThat( "this information can appear on some carrier's shipping labels. See link below").isEqualTo(order.getAdvancedOptions().getCustomField2());
        assertThat( "https://help.shipstation.com/hc/en-us/articles/206639957").isEqualTo(order.getAdvancedOptions().getCustomField3());
        assertThat( "Webstore").isEqualTo(order.getAdvancedOptions().getSource());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToParty());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToAccount());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToPostalCode());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToCountryCode());
        //assertThat( null).isEqualTo(order.getTagIds());
        //assertThat( null).isEqualTo(order.getUserId());
        assertThat( false).isEqualTo(order.getExternallyFulfilled());
        //assertThat( null).isEqualTo(order.getExternallyFulfilledBy());

    }

    @Test
    public void testDeleteOrder() throws IOException, InterruptedException {

        SuccessResponse response = api.deleteOrder(123456789);

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "The requested order has been deleted.").isEqualTo(response.getMessage());

    }

    @Test
    public void testAddTagToOrder() throws IOException, InterruptedException {

        SuccessResponse response = api.addTagToOrder(123456, 1234);

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "Tag added successfully.").isEqualTo(response.getMessage());

    }

    @Test
    public void testAssignUserToOrder() throws IOException, InterruptedException {

        UserToOrderPayload payload = new UserToOrderPayload();
        Long orderIds[] = {123456789L, 12345679L};
        payload.setOrderIds(orderIds);
        payload.setUserid("123456AB-ab12-3c4d-5e67-89f1abc1defa");

        SuccessResponse response = api.assignUserToOrder(payload);

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "User assigned successfully.").isEqualTo(response.getMessage());

    }

    @Test
    public void testCreateLabelForOrder() throws IOException, InterruptedException {

        LabelForOrderPayload payload = new LabelForOrderPayload();
        payload.setOrderId(93348442L);
        payload.setCarrierCode("fedex");
        payload.setServiceCode("fedex_2day");
        payload.setConfirmation(null);
        payload.setShipDate("2014-04-03");
        Weight weight = new Weight();
        weight.setValue(2L);
        weight.setUnits("pounds");
        payload.setWeight(weight);
        payload.setTestLabel(false);

        Label label = api.createLabelForOrder(payload);

        assertThat( 72513480).isEqualTo(label.getShipmentId());
        assertThat( 0.01).isEqualTo(label.getShipmentCost());
        assertThat( 0.01).isEqualTo(label.getInsuranceCost());
        assertThat( "248201115029520").isEqualTo(label.getTrackingNumber());
        assertThat( "JVBERi0xLjQKJeLjz9MKMiAwIG9iago8PC9MZW5ndGggNjIvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwr5HIK4TI2UzC2NFMISeFyDeEK5CpUMFQwAEJDBV0jCz0LBV1jY0M9I4XkXAX9iDRDBZd8hUAuAEdGC7cKZW5kc3RyZWFtCmVuZG9iago0IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveFswIDAgMjg4IDQzMl0vUmVzb3VyY2VzPDwvUHJvY1NldCBbL1BERiAvVGV4dCAvSW1hZ2VCIC9JbWFnZUMgL0ltYWdlSV0vWE9iamVjdDw8L1hmMSAxIDAgUj4+Pj4vQ29udGVudHMgMiAwIFIvUGFyZW50....").isEqualTo(label.getLabelData());
        //assertThat( null).isEqualTo(label.getFormData());

    }

    @Test
    public void testOrderCreate() throws IOException, InterruptedException {

        Order order = new Order();
        order.setOrderId(140335319L);
        order.setOrderNumber("TEST-ORDER-API-DOCS");

        Order reply = api.createOrder(order);

        assertThat( 140335319L).isEqualTo(reply.getOrderId());
        assertThat( "TEST-ORDER-API-DOCS").isEqualTo(reply.getOrderNumber());
    }

    @Test
    public void testCreateOrders() throws IOException, InterruptedException {

        List<Order> orders = new ArrayList<Order>();

        Order order1 = new Order();
        order1.setOrderId(140335319L);
        order1.setOrderNumber("TEST-ORDER-API-DOCS");
        orders.add(order1);

        Order order2 = new Order();
        order2.setOrderId(140335319L);
        order2.setOrderNumber("TEST-ORDER-API-DOCS");
        orders.add(order2);

        BulkOrdersResponse reply = api.createOrders(orders);

        assertThat( false).isEqualTo(reply.getHasErrors());
        assertThat( 58345234L).isEqualTo(reply.getResults().get(0).getOrderId());
        assertThat( "TEST-ORDER-API-DOCS").isEqualTo(reply.getResults().get(0).getOrderNumber());
        assertThat( "0f6bec18-3e89-4881-83aa-f392d84f4c74").isEqualTo(reply.getResults().get(0).getOrderKey());
        assertThat( true).isEqualTo(reply.getResults().get(0).getSuccess());
        //assertThat( null).isEqualTo(reply.getResults().get(0).getErrorMessage());


    }

    @Test
    public void tesHoldOrderUntil() throws IOException, InterruptedException {

        SuccessResponse response = api.holdOrderUntil(1072467, "2014-12-01");

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "Order held successfully.").isEqualTo(response.getMessage());

    }

    @Test
    public void testListOrder() throws IOException, InterruptedException {

        ListOrders listOrders = api.listOrders();

        Order order = listOrders.getOrders().get(0);

        assertThat( 987654321L).isEqualTo(order.getOrderId());
        assertThat( "Test-International-API-DOCS").isEqualTo(order.getOrderNumber());
        assertThat( "Test-International-API-DOCS").isEqualTo(order.getOrderKey());
        assertThat( "2015-06-28T17:46:27.0000000").isEqualTo(order.getOrderDate());
        assertThat( "2015-08-17T09:24:14.7800000").isEqualTo(order.getCreateDate());
        assertThat( "2015-08-17T09:24:16.4800000").isEqualTo(order.getModifyDate());
        assertThat( "2015-06-28T17:46:27.0000000").isEqualTo(order.getPaymentDate());
        assertThat( "2015-07-05T00:00:00.0000000").isEqualTo(order.getShipByDate());
        assertThat( Order.STATUS.awaiting_shipment).isEqualTo(order.getOrderStatus());
        assertThat( 63310475).isEqualTo(order.getCustomerId());
        assertThat( "sholmes1854@methodsofdetection.com").isEqualTo(order.getCustomerUsername());
        assertThat( "sholmes1854@methodsofdetection.com").isEqualTo(order.getCustomerUsername());

        assertThat( "Sherlock Holmes").isEqualTo(order.getBillTo().getName());
        //assertThat( null).isEqualTo(order.getBillTo().getCompany());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet1());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet2());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet3());
        //assertThat( null).isEqualTo(order.getBillTo().getCity());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getPostalCode());
        //assertThat( null).isEqualTo(order.getBillTo().getPhone());
        assertThat( false).isEqualTo(order.getBillTo().getResidential());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getAddressVerified());

        assertThat( "Sherlock Holmes").isEqualTo(order.getShipTo().getName());
        assertThat( "").isEqualTo(order.getShipTo().getCompany());
        assertThat( "221 B Baker St").isEqualTo(order.getShipTo().getStreet1());
        assertThat( "").isEqualTo(order.getShipTo().getStreet2());
        //assertThat( null).isEqualTo(order.getShipTo().getStreet3());
        assertThat( "London").isEqualTo(order.getShipTo().getCity());
        assertThat( "").isEqualTo(order.getShipTo().getState());
        assertThat( "NW1 6XE").isEqualTo(order.getShipTo().getPostalCode());
        assertThat( "GB").isEqualTo(order.getShipTo().getCountry());
        //assertThat( null).isEqualTo(order.getShipTo().getPhone());
        assertThat( true).isEqualTo(order.getShipTo().getResidential());
        assertThat( "Address not yet validated").isEqualTo(order.getShipTo().getAddressVerified());

        List<OrderItem> items = order.getItems();

        assertThat( 136282568).isEqualTo(items.get(0).getOrderItemId());
        //assertThat( null).isEqualTo(items.get(0).getLineItemKey());
        assertThat( "Ele-1234").isEqualTo(items.get(0).getSku());
        assertThat( "Elementary Disguise Kit").isEqualTo(items.get(0).getName());
        //assertThat( null).isEqualTo(items.get(0).getImageUrl());
        assertThat( 12).isEqualTo(items.get(0).getWeight().getValue());
        assertThat( "ounces").isEqualTo(items.get(0).getWeight().getUnits());
        assertThat( 0.01).isEqualTo(items.get(0).getQuantity());
        assertThat( 0.01).isEqualTo(items.get(0).getUnitPrice());
        assertThat( 0.01).isEqualTo(items.get(0).getTaxAmount());//null double is 0.0
        assertThat( 0.01).isEqualTo(items.get(0).getShippingAmount());//null double is 0.0
        assertThat( "Bin 7").isEqualTo(items.get(0).getWarehouseLocation());
        assertThat( 0.01).isEqualTo(order.getOrderTotal());
        assertThat( 0.01).isEqualTo(order.getAmountPaid());
        assertThat( 0.01).isEqualTo(order.getTaxAmount());
        assertThat( 0.01).isEqualTo(order.getShippingAmount());
        assertThat( "Please be careful when packing the disguise kits in with the cane.").isEqualTo(order.getCustomerNotes());
        assertThat( "Mr. Holmes called to upgrade his shipping to expedited").isEqualTo(order.getInternalNotes());
        assertThat( false).isEqualTo(order.getGift());
        //assertThat( null).isEqualTo(order.getGiftMessage());
        //assertThat( null).isEqualTo(order.getPaymentMethod());
        assertThat( "Priority Mail Int").isEqualTo(order.getRequestedShippingService());
        assertThat( "stamps_com").isEqualTo(order.getCarrierCode());
        assertThat( "usps_priority_mail_international").isEqualTo(order.getServiceCode());
        assertThat( "package").isEqualTo(order.getPackageCode());
        assertThat( "delivery").isEqualTo(order.getConfirmation());
        assertThat( "2015-04-25").isEqualTo(order.getShipDate());
        //assertThat( null).isEqualTo(order.getHoldUntilDate());
        assertThat( 104).isEqualTo(order.getWeight().getValue());
        assertThat( "ounces").isEqualTo(order.getWeight().getUnits());
        assertThat( "inches").isEqualTo(order.getDimensions().getUnits());
        assertThat( 0.1).isEqualTo(order.getDimensions().getLength());
        assertThat( 0.1).isEqualTo(order.getDimensions().getWidth());
        assertThat( 0.1).isEqualTo(order.getDimensions().getHeight());
        //assertThat( null).isEqualTo(order.getInsuranceOptions().getProvider());
        assertThat( false).isEqualTo(order.getInsuranceOptions().getInsureShipment());
        assertThat( 0.01).isEqualTo(order.getInsuranceOptions().getInsuredValue());
        assertThat( "merchandise").isEqualTo(order.getInternationalOptions().getContents());
        assertThat( "11558268").isEqualTo(order.getInternationalOptions().getCustomsItems().get(0).getCustomsItemId());
        assertThat( "Fine White Oak Cane").isEqualTo(order.getInternationalOptions().getCustomsItems().get(0).getDescription());
        assertThat( 1).isEqualTo(order.getInternationalOptions().getCustomsItems().get(0).getQuantity());
        assertThat( 0.01).isEqualTo(order.getInternationalOptions().getCustomsItems().get(0).getValue());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getCustomsItems().get(0).getHarmonizedTariffCode());
        assertThat( "US").isEqualTo(order.getInternationalOptions().getCustomsItems().get(0).getCountryOfOrigin());
        assertThat( "11558267").isEqualTo(order.getInternationalOptions().getCustomsItems().get(1).getCustomsItemId());
        assertThat( "Elementary Disguise Kit").isEqualTo(order.getInternationalOptions().getCustomsItems().get(1).getDescription());
        assertThat( 2).isEqualTo(order.getInternationalOptions().getCustomsItems().get(1).getQuantity());
        assertThat( 0.01).isEqualTo(order.getInternationalOptions().getCustomsItems().get(1).getValue());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getCustomsItems().get(1).getHarmonizedTariffCode());
        assertThat( "US").isEqualTo(order.getInternationalOptions().getCustomsItems().get(1).getCountryOfOrigin());
        assertThat( "return_to_sender").isEqualTo(order.getInternationalOptions().getNonDelivery());

        assertThat( 98765).isEqualTo(order.getAdvancedOptions().getWarehouseId());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getNonMachinable());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getSaturdayDelivery());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getContainsAlcohol());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getMergedOrSplit());
        assertThat( 0).isEqualTo(order.getAdvancedOptions().getMergedIds().length); //checking length, cant compare Long[]
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getParentId());
        assertThat( 12345).isEqualTo(order.getAdvancedOptions().getStoreId());
        assertThat( "SKU: CN-9876 x 1").isEqualTo(order.getAdvancedOptions().getCustomField1());
        assertThat( "SKU: Ele-123 x 2").isEqualTo(order.getAdvancedOptions().getCustomField2());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getCustomField3());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getSource());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToParty());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToAccount());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToPostalCode());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToCountryCode());
        //assertThat( null).isEqualTo(order.getTagIds());
        //assertThat( null).isEqualTo(order.getUserId());
        assertThat( false).isEqualTo(order.getExternallyFulfilled());
        //assertThat( null).isEqualTo(order.getExternallyFulfilledBy());

        assertThat( 2).isEqualTo(listOrders.getTotal());
        assertThat( 1).isEqualTo(listOrders.getPage());
        assertThat( 0).isEqualTo(listOrders.getPages());

    }

    @Test
    public void testListOrderByStatus() throws IOException, InterruptedException {

        ListOrders listOrders = api.listOrdersByStatus(Order.STATUS.shipped);
        Order order = listOrders.getOrders().get(0);

        assertThat( 987654321L).isEqualTo(order.getOrderId());
        assertThat( "Test-International-API-DOCS").isEqualTo(order.getOrderNumber());
        assertThat( "Test-International-API-DOCS").isEqualTo(order.getOrderKey());
        // Mock API returns an invalid response for this field
        //assertThat( Order.STATUS.awaiting_shipment).isEqualTo(order.getOrderStatus());
        assertThat( 63310475).isEqualTo(order.getCustomerId());
        assertThat( "sholmes1854@methodsofdetection.com").isEqualTo(order.getCustomerUsername());
        assertThat( "Sherlock Holmes").isEqualTo(order.getBillTo().getName());
        //assertThat( null).isEqualTo(order.getBillTo().getCompany());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet1());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet2());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet3());
        //assertThat( null).isEqualTo(order.getBillTo().getCity());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getPostalCode());
        //assertThat( null).isEqualTo(order.getBillTo().getPhone());
        assertThat( false).isEqualTo(order.getBillTo().getResidential());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getAddressVerified());

        assertThat( "Sherlock Holmes").isEqualTo(order.getShipTo().getName());
        assertThat( "").isEqualTo(order.getShipTo().getCompany());
        assertThat( "221 B Baker St").isEqualTo(order.getShipTo().getStreet1());
        assertThat( "").isEqualTo(order.getShipTo().getStreet2());
        //assertThat( null).isEqualTo(order.getShipTo().getStreet3());
        assertThat( "London").isEqualTo(order.getShipTo().getCity());
        assertThat( "").isEqualTo(order.getShipTo().getState());
        assertThat( "NW1 6XE").isEqualTo(order.getShipTo().getPostalCode());
        //assertThat( null).isEqualTo(order.getShipTo().getPhone());
        assertThat( true).isEqualTo(order.getShipTo().getResidential());
        assertThat( "Address not yet validated").isEqualTo(order.getShipTo().getAddressVerified());

        List<OrderItem> items = order.getItems();

        assertThat( 2).isEqualTo(items.size());

        assertThat( 2).isEqualTo(listOrders.getTotal());
        assertThat( 1).isEqualTo(listOrders.getPage());
        assertThat( 0).isEqualTo(listOrders.getPages());
    }

    @Test
    public void testListOrderByTag() throws IOException, InterruptedException {

        ListOrders listOrders = api.listOrdersByTag("orderStatus=awaiting_payment");
        Order order = listOrders.getOrders().get(0);

        assertThat( 123456789L).isEqualTo(order.getOrderId());
        assertThat( "TEST-ORDER-API-DOCS").isEqualTo(order.getOrderNumber());
        assertThat( "0f6bec18-3e89-4881-83aa-f392d84f4c74").isEqualTo(order.getOrderKey());
        assertThat( "2015-06-29T08:46:27.0000000").isEqualTo(order.getOrderDate());
        assertThat( "2015-08-17T09:43:03.0500000").isEqualTo(order.getCreateDate());
        assertThat( "2015-08-17T09:43:12.5500000").isEqualTo(order.getModifyDate());
        assertThat( "2015-06-29T08:46:27.0000000").isEqualTo(order.getPaymentDate());
        assertThat( "2015-07-05T00:00:00.0000000").isEqualTo(order.getShipByDate());
        assertThat( Order.STATUS.awaiting_shipment).isEqualTo(order.getOrderStatus());
        assertThat( 37701499).isEqualTo(order.getCustomerId());
        assertThat( "headhoncho@whitehouse.gov").isEqualTo(order.getCustomerUsername());
        assertThat( "The President").isEqualTo(order.getBillTo().getName());
        //assertThat( null).isEqualTo(order.getBillTo().getCompany());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet1());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet2());
        //assertThat( null).isEqualTo(order.getBillTo().getStreet3());
        //assertThat( null).isEqualTo(order.getBillTo().getCity());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getPostalCode());
        //assertThat( null).isEqualTo(order.getBillTo().getPhone());
        assertThat( false).isEqualTo(order.getBillTo().getResidential());
        //assertThat( null).isEqualTo(order.getBillTo().getState());
        //assertThat( null).isEqualTo(order.getBillTo().getAddressVerified());

        assertThat( "The President").isEqualTo(order.getShipTo().getName());
        assertThat( "US Govt").isEqualTo(order.getShipTo().getCompany());
        assertThat( "1600 Pennsylvania Ave").isEqualTo(order.getShipTo().getStreet1());
        assertThat( "Oval Office").isEqualTo(order.getShipTo().getStreet2());
        //assertThat( null).isEqualTo(order.getShipTo().getStreet3());
        assertThat( "Washington").isEqualTo(order.getShipTo().getCity());
        assertThat( "DC").isEqualTo(order.getShipTo().getState());
        assertThat( "20500").isEqualTo(order.getShipTo().getPostalCode());
        assertThat( "555-555-5555").isEqualTo(order.getShipTo().getPhone());
        assertThat( false).isEqualTo(order.getShipTo().getResidential());
        assertThat( "DC").isEqualTo(order.getShipTo().getState());
        assertThat( "Address validation warning").isEqualTo(order.getShipTo().getAddressVerified());

        List<OrderItem> items = order.getItems();

        assertThat( 136289188).isEqualTo(items.get(0).getOrderItemId());
        assertThat( "vd08-MSLbtx").isEqualTo(items.get(0).getLineItemKey());
        assertThat( "ABC123").isEqualTo(items.get(0).getSku());
        assertThat( "Test item #1").isEqualTo(items.get(0).getName());
        //assertThat( null).isEqualTo(items.get(0).getImageUrl());
        assertThat( 24).isEqualTo(items.get(0).getWeight().getValue());
        assertThat( "ounces").isEqualTo(items.get(0).getWeight().getUnits());
        assertThat( 0.01).isEqualTo(items.get(0).getQuantity());
        assertThat( 0.01).isEqualTo(items.get(0).getUnitPrice());
        assertThat( 0.01).isEqualTo(items.get(0).getTaxAmount());
        assertThat( 0.01).isEqualTo(items.get(0).getShippingAmount());
        assertThat( "Bin 7").isEqualTo(items.get(0).getWarehouseLocation());
        assertThat( "Size").isEqualTo(items.get(0).getOptions().get(0).getName());
        assertThat( "Large").isEqualTo(items.get(0).getOptions().get(0).getValue());
        assertThat( 0.01).isEqualTo(order.getOrderTotal());
        assertThat( 0.01).isEqualTo(order.getAmountPaid());
        assertThat( 0.01).isEqualTo(order.getTaxAmount());
        assertThat( 0.01).isEqualTo(order.getShippingAmount());
        assertThat( "Please ship as soon as possible!").isEqualTo(order.getCustomerNotes());
        assertThat( "Customer called and would like to upgrade shipping").isEqualTo(order.getInternalNotes());
        assertThat( true).isEqualTo(order.getGift());
        assertThat( "Thank you!").isEqualTo(order.getGiftMessage());
        assertThat( "Credit Card").isEqualTo(order.getPaymentMethod());
        assertThat( "Priority Mail").isEqualTo(order.getRequestedShippingService());
        assertThat( "fedex").isEqualTo(order.getCarrierCode());
        assertThat( "fedex_2day").isEqualTo(order.getServiceCode());
        assertThat( "package").isEqualTo(order.getPackageCode());
        assertThat( "delivery").isEqualTo(order.getConfirmation());
        assertThat( "2015-07-02").isEqualTo(order.getShipDate());
        //assertThat( null).isEqualTo(order.getHoldUntilDate());
        assertThat( 48).isEqualTo(order.getWeight().getValue());
        assertThat( "ounces").isEqualTo(order.getWeight().getUnits());
        assertThat( "inches").isEqualTo(order.getDimensions().getUnits());
        assertThat( 0.1).isEqualTo(order.getDimensions().getLength());
        assertThat( 0.1).isEqualTo(order.getDimensions().getWidth());
        assertThat( 0.1).isEqualTo(order.getDimensions().getHeight());
        assertThat( "carrier").isEqualTo(order.getInsuranceOptions().getProvider());
        assertThat( true).isEqualTo(order.getInsuranceOptions().getInsureShipment());
        assertThat( 0.01).isEqualTo(order.getInsuranceOptions().getInsuredValue());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getContents());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getCustomsItems());
        //assertThat( null).isEqualTo(order.getInternationalOptions().getNonDelivery());
        assertThat( 98765).isEqualTo(order.getAdvancedOptions().getWarehouseId());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getNonMachinable());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getSaturdayDelivery());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getContainsAlcohol());
        assertThat( false).isEqualTo(order.getAdvancedOptions().getMergedOrSplit());
        assertThat( 0).isEqualTo(order.getAdvancedOptions().getMergedIds().length); //checking length, cant compare Long[]
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getParentId());
        assertThat( 12345L).isEqualTo(order.getAdvancedOptions().getStoreId());
        assertThat( "Custom data that you can add to an order. See Custom Field #2 & #3 for more info!").isEqualTo(order.getAdvancedOptions().getCustomField1());
        assertThat( "this information can appear on some carrier's shipping labels. See link below").isEqualTo(order.getAdvancedOptions().getCustomField2());
        assertThat( "https://help.shipstation.com/hc/en-us/articles/206639957").isEqualTo(order.getAdvancedOptions().getCustomField3());
        assertThat( "Webstore").isEqualTo(order.getAdvancedOptions().getSource());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToParty());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToAccount());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToPostalCode());
        //assertThat( null).isEqualTo(order.getAdvancedOptions().getBillToCountryCode());
        assertThat( 831L).isEqualTo(order.getTagIds()[0]);
        assertThat( "b854f701-e0c2-4156-90fe-19c77cdef27c").isEqualTo(order.getUserId());
        assertThat( false).isEqualTo(order.getExternallyFulfilled());
        //assertThat( null).isEqualTo(order.getExternallyFulfilledBy());

        assertThat( 1).isEqualTo(listOrders.getTotal());
        assertThat( 1).isEqualTo(listOrders.getPage());
        assertThat( 1).isEqualTo(listOrders.getPages());
    }

    @Test
    public void testMarkAnOrderAsShipped() throws IOException, InterruptedException {

        OrderAsShippedPayload orderAsShippedPayload = new OrderAsShippedPayload();
        orderAsShippedPayload.setOrderId(93348442L);
        orderAsShippedPayload.setCarrierCode("usps");
        orderAsShippedPayload.setShipDate("2014-04-01");
        orderAsShippedPayload.setTrackingNumber("913492493294329421");
        orderAsShippedPayload.setNotifyCustomer(true);
        orderAsShippedPayload.setNotifySalesChannel(true);

        OrderAsShippedResponse orderAsShippedResponse = api.markAnOrderAsShipped(orderAsShippedPayload);

        assertThat( 123456789L).isEqualTo(orderAsShippedResponse.getOrderId());
        assertThat( "ABC123").isEqualTo(orderAsShippedResponse.getOrderNumber());

    }

    @Test
    public void testRemoveTagFromOrder() throws IOException, InterruptedException {

        SuccessResponse response = api.removeTagFromOrder(123456, 1234);
        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "Tag removed successfully.").isEqualTo(response.getMessage());

    }

    @Test
    public void testnRestoreOrderFromOnHold() throws IOException, InterruptedException {

        SuccessResponse response = api.restoreOrderFromOnHold(1234567);
        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "The requested order has been restored").isEqualTo(response.getMessage());

    }

    @Test
    public void testUnassignUserFromOrder() throws IOException, InterruptedException {

        List<String> orderIds = new ArrayList<String>();
        orderIds.add("123456789");
        orderIds.add("12345679");

        SuccessResponse response = api.unassignUserFromOrder(orderIds);
        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "User unassigned successfully.").isEqualTo(response.getMessage());

    }

    @Test
    public void testGetProduct() throws IOException, InterruptedException {

        Product product = api.getProduct(12345678);

        assertThat( 12345678).isEqualTo(product.getProductId());
        assertThat( "1004").isEqualTo(product.getSku());
        assertThat( "Coffee Mug").isEqualTo(product.getName());
        assertThat( 0.01).isEqualTo(product.getPrice());
        assertThat( 0.01).isEqualTo(product.getDefaultCost());
        assertThat( 0.01).isEqualTo(product.getLength());
        assertThat( 0.01).isEqualTo(product.getWidth());
        assertThat( 0.01).isEqualTo(product.getHeight());
        assertThat( 0.01).isEqualTo(product.getWeightOz());
        //assertThat( null).isEqualTo(product.getInternalNotes());
        assertThat( "F1004").isEqualTo(product.getFulfillmentSku());
        assertThat( "2014-09-04T09:18:01.293").isEqualTo(product.getCreateDate());
        assertThat( "2014-09-18T12:38:43.893").isEqualTo(product.getModifyDate());
        assertThat( true).isEqualTo(product.getActive());
        assertThat( 9999).isEqualTo(product.getProductCategory().getCategoryId());
        assertThat( "Door Closers").isEqualTo(product.getProductCategory().getName());
        //assertThat( null).isEqualTo(product.getProductType());
        assertThat( "Bin 22").isEqualTo(product.getWarehouseLocation());
        assertThat( "fedex").isEqualTo(product.getDefaultCarrierCode());
        assertThat( "fedex_home_delivery").isEqualTo(product.getDefaultServiceCode());
        assertThat( "package").isEqualTo(product.getDefaultPackageCode());
        assertThat( "ups").isEqualTo(product.getDefaultIntlCarrierCode());
        assertThat( "ups_worldwide_saver").isEqualTo(product.getDefaultIntlServiceCode());
        assertThat( "package").isEqualTo(product.getDefaultIntlPackageCode());
        assertThat( "direct_signature").isEqualTo(product.getDefaultConfirmation());
        assertThat( "adult_signature").isEqualTo(product.getDefaultIntlConfirmation());
        //assertThat( null).isEqualTo(product.getCustomsDescription());
        assertThat( 0.01).isEqualTo(product.getCustomsValue());//null double is 0.0
        //assertThat( null).isEqualTo(product.getCustomsCountryCode());
        assertThat( false).isEqualTo(product.getNoCustoms());//null boolean is false
        assertThat( 9180).isEqualTo(product.getTags().get(0).getTagId());
        assertThat( "APItest").isEqualTo(product.getTags().get(0).getName());

    }

    @Test
    public void testUpdateProduct() throws IOException, InterruptedException {

        Product product = new Product();
        product.setProductId(123456789L);
        product.setName("Beautiful");

        SuccessResponse response = api.updateProduct(product);
        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "The requested product has been updated").isEqualTo(response.getMessage());

    }

    @Test
    public void testListProducts() throws IOException, InterruptedException {

        ListProducts listProducts = api.listProducts();

        Product product = listProducts.getProducts().get(0);

        assertThat( 7854008).isEqualTo(product.getProductId());
        assertThat( "1004").isEqualTo(product.getSku());
        assertThat( "Coffee Mug").isEqualTo(product.getName());
        assertThat( 0.01).isEqualTo(product.getPrice());
        assertThat( 0.01).isEqualTo(product.getDefaultCost());
        assertThat( 0.01).isEqualTo(product.getLength());
        assertThat( 0.01).isEqualTo(product.getWidth());
        assertThat( 0.01).isEqualTo(product.getHeight());
        assertThat( 0.01).isEqualTo(product.getWeightOz());
        //assertThat( null).isEqualTo(product.getInternalNotes());
        assertThat( "F1004").isEqualTo(product.getFulfillmentSku());
        assertThat( "2014-09-04T09:18:01.293").isEqualTo(product.getCreateDate());
        assertThat( "2014-09-18T12:38:43.893").isEqualTo(product.getModifyDate());
        assertThat( true).isEqualTo(product.getActive());
        assertThat( 9999).isEqualTo(product.getProductCategory().getCategoryId());
        assertThat( "Door Closers").isEqualTo(product.getProductCategory().getName());
        //assertThat( null).isEqualTo(product.getProductType());
        assertThat( "Bin 22").isEqualTo(product.getWarehouseLocation());
        assertThat( "fedex").isEqualTo(product.getDefaultCarrierCode());
        assertThat( "fedex_home_delivery").isEqualTo(product.getDefaultServiceCode());
        assertThat( "package").isEqualTo(product.getDefaultPackageCode());
        assertThat( "ups").isEqualTo(product.getDefaultIntlCarrierCode());
        assertThat( "ups_worldwide_saver").isEqualTo(product.getDefaultIntlServiceCode());
        assertThat( "package").isEqualTo(product.getDefaultIntlPackageCode());
        assertThat( "direct_signature").isEqualTo(product.getDefaultConfirmation());
        assertThat( "adult_signature").isEqualTo(product.getDefaultIntlConfirmation());
        //assertThat( null).isEqualTo(product.getCustomsDescription());
        assertThat( 0.01).isEqualTo(product.getCustomsValue());//null double is 0.0
        //assertThat( null).isEqualTo(product.getCustomsCountryCode());
        assertThat( false).isEqualTo(product.getNoCustoms());//null boolean is false
        assertThat( 9180).isEqualTo(product.getTags().get(0).getTagId());
        assertThat( "APItest").isEqualTo(product.getTags().get(0).getName());

        assertThat( 2).isEqualTo(listProducts.getTotal());
        assertThat( 1).isEqualTo(listProducts.getPage());
        assertThat( 0).isEqualTo(listProducts.getPages());

    }

    @Test
    public void testListShipments() throws IOException, InterruptedException {

        ListShipments listShipments = api.listShipments();
        Shipment shipment = listShipments.getShipments().get(0);

        assertThat( 33974374L).isEqualTo(shipment.getShipmentId());
        assertThat( 43945660L).isEqualTo(shipment.getOrderId());
        assertThat( "123456AB-ab12-3c4d-5e67-89f1abc1defa").isEqualTo(shipment.getUserId());
        assertThat( "100038-1").isEqualTo(shipment.getOrderNumber());
        assertThat( "2014-10-03T06:51:33.6270000").isEqualTo(shipment.getCreateDate());
        assertThat( "2014-10-03").isEqualTo(shipment.getShipDate());
        assertThat( 0.01).isEqualTo(shipment.getShipmentCost());
        assertThat( 0.01).isEqualTo(shipment.getInsuranceCost());
        assertThat( "9400111899561704681189").isEqualTo(shipment.getTrackingNumber());
        assertThat( false).isEqualTo(shipment.getReturnLabel());
        assertThat( "100301").isEqualTo(shipment.getBatchNumber());
        assertThat( "stamps_com").isEqualTo(shipment.getCarrierCode());
        assertThat( "usps_first_class_mail").isEqualTo(shipment.getServiceCode());
        assertThat( "package").isEqualTo(shipment.getPackageCode());
        assertThat( "delivery").isEqualTo(shipment.getConfirmation());
        assertThat( 16079).isEqualTo(shipment.getWarehouseId());
        assertThat( false).isEqualTo(shipment.getVoided());
        //assertThat( null).isEqualTo(shipment.getVoidDate());
        assertThat( true).isEqualTo(shipment.getMarketplaceNotified());
        //assertThat( null).isEqualTo(shipment.getNotifyErrorMessage());
        assertThat( "Yoda").isEqualTo(shipment.getShipTo().getName());
        assertThat( "").isEqualTo(shipment.getShipTo().getCompany());
        assertThat( "12223 LOWDEN LN").isEqualTo(shipment.getShipTo().getStreet1());
        assertThat( "").isEqualTo(shipment.getShipTo().getStreet2());
        //assertThat( null).isEqualTo(shipment.getShipTo().getStreet3());
        assertThat( "MANCHACA").isEqualTo(shipment.getShipTo().getCity());
        assertThat( "TX").isEqualTo(shipment.getShipTo().getState());
        assertThat( "78652-3602").isEqualTo(shipment.getShipTo().getPostalCode());
        assertThat( "US").isEqualTo(shipment.getShipTo().getCountry());
        assertThat( "2101235544").isEqualTo(shipment.getShipTo().getPhone());
        assertThat( false).isEqualTo(shipment.getShipTo().getResidential());//null boolean is false
        assertThat( 1).isEqualTo(shipment.getWeight().getValue());
        assertThat( "ounces").isEqualTo(shipment.getWeight().getUnits());
        //assertThat( null).isEqualTo(shipment.getDimensions());
        //assertThat( null).isEqualTo(shipment.getInsuranceOptions().getProvider());
        //assertThat( null).isEqualTo(shipment.getInsuranceOptions().getProvider());
        assertThat( false).isEqualTo(shipment.getInsuranceOptions().getInsureShipment());
        assertThat( 0.01).isEqualTo(shipment.getInsuranceOptions().getInsuredValue());
        //assertThat( null).isEqualTo(shipment.getAdvancedOptions());
        assertThat( 56568665).isEqualTo(shipment.getShipmentItems().get(0).getOrderItemId());
        //assertThat( null).isEqualTo(shipment.getShipmentItems().get(0).getLineItemKey());
        assertThat( "SQ3785739").isEqualTo(shipment.getShipmentItems().get(0).getSku());
        assertThat( "Potato Kitten -").isEqualTo(shipment.getShipmentItems().get(0).getName());
        //assertThat( null).isEqualTo(shipment.getShipmentItems().get(0).getImageUrl());
        //assertThat( null).isEqualTo(shipment.getShipmentItems().get(0).getWeight());
        assertThat( 0.01).isEqualTo(shipment.getShipmentItems().get(0).getQuantity());
        assertThat( 0.01).isEqualTo(shipment.getShipmentItems().get(0).getUnitPrice());
        //assertThat( null).isEqualTo(shipment.getShipmentItems().get(0).getWarehouseLocation());
        //assertThat( null).isEqualTo(shipment.getShipmentItems().get(0).getOptions());
        assertThat( 7565777).isEqualTo(shipment.getShipmentItems().get(0).getProductId());
        //assertThat( null).isEqualTo(shipment.getShipmentItems().get(0).getFulfillmentSku());
        //assertThat( null).isEqualTo(shipment.getLabelData());
        //assertThat( null).isEqualTo(shipment.getFormData());

    }

    @Test
    public void testCreateShipmentLabel() throws IOException, InterruptedException {

        ShipmentLabelPayload shipmentLabelPayload = new ShipmentLabelPayload();
        shipmentLabelPayload.setCarrierCode("fedex");
        shipmentLabelPayload.setServiceCode("fedex_ground");
        shipmentLabelPayload.setPackageCode("package");
        shipmentLabelPayload.setConfirmation("delivery");
        //etc...

        Shipment shipment = api.createShipmentLabel(shipmentLabelPayload);

        assertThat( 123456789L).isEqualTo(shipment.getShipmentId());
        assertThat( 0).isEqualTo(shipment.getOrderId());//null Long is 0
        //assertThat( null).isEqualTo(shipment.getUserId());
        //assertThat( null).isEqualTo(shipment.getOrderNumber());
        assertThat( "2016-04-03T12:11:36.8630000").isEqualTo(shipment.getCreateDate());
        assertThat( "2016-04-03").isEqualTo(shipment.getShipDate());
        assertThat( 0.01).isEqualTo(shipment.getShipmentCost());
        assertThat( 0.01).isEqualTo(shipment.getInsuranceCost());
        assertThat( "782390443992").isEqualTo(shipment.getTrackingNumber());
        assertThat( false).isEqualTo(shipment.getReturnLabel());
        //assertThat( null).isEqualTo(shipment.getBatchNumber());
        assertThat( "fedex").isEqualTo(shipment.getCarrierCode());
        assertThat( "fedex_ground").isEqualTo(shipment.getServiceCode());
        assertThat( "package").isEqualTo(shipment.getPackageCode());
        assertThat( "delivery").isEqualTo(shipment.getConfirmation());
        assertThat( 0).isEqualTo(shipment.getWarehouseId());//null Long is 0
        assertThat( false).isEqualTo(shipment.getVoided());
        //assertThat( null).isEqualTo(shipment.getVoidDate());
        assertThat( false).isEqualTo(shipment.getMarketplaceNotified());
        //assertThat( null).isEqualTo(shipment.getNotifyErrorMessage());
        //assertThat( null).isEqualTo(shipment.getShipTo());
        //assertThat( null).isEqualTo(shipment.getWeight());
        //assertThat( null).isEqualTo(shipment.getDimensions());
        //assertThat( null).isEqualTo(shipment.getInsuranceOptions());
        //assertThat( null).isEqualTo(shipment.getAdvancedOptions());
        //assertThat( null).isEqualTo(shipment.getShipmentItems());
        assertThat( "JVBERi0xLjQKJeLjz9MKMiAwIG9iago8PC9MZW5ndGggNjIvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwr5HIK4TI2U...").isEqualTo(shipment.getLabelData());
        //assertThat( null).isEqualTo(shipment.getFormData());

    }

    @Test
    public void testGetRates() throws IOException, InterruptedException {

        RatePayload rate = new RatePayload();
        rate.setCarrierCode("fedex");
        rate.setFromPostalCode("78703");
        rate.setToState("DC");
        rate.setToCountry("US");
        rate.setToPostalCode("20500");
        //etc...

        List<Rate> rates = api.getRates(rate);

        assertThat( "FedEx First Overnight" + Character.toString((char)174)).isEqualTo(rates.get(0).getServiceName());
        assertThat( "fedex_first_overnight").isEqualTo(rates.get(0).getServiceCode());
        assertThat( 0.01).isEqualTo(rates.get(0).getShipmentCost());
        assertThat( 0.001).isEqualTo(rates.get(0).getOtherCost());

        assertThat( "FedEx Priority Overnight" + Character.toString((char)174)).isEqualTo(rates.get(1).getServiceName());
        assertThat( "fedex_priority_overnight").isEqualTo(rates.get(1).getServiceCode());
        assertThat( 0.01).isEqualTo(rates.get(1).getShipmentCost());
        assertThat( 0.001).isEqualTo(rates.get(1).getOtherCost());

    }

    @Test
    public void testVoidLabel() throws IOException, InterruptedException {

        VoidLabelResponse response = api.voidLabel(12345);
        assertThat( true).isEqualTo(response.getApproved());
        assertThat( "Label voided successfully").isEqualTo(response.getMessage());

    }

    @Test
    public void testGetStore() throws IOException, InterruptedException {

        Store store = api.getStore(12345);

        assertThat( 12345).isEqualTo(store.getStoreId());
        assertThat( "WooCommerce Store").isEqualTo(store.getStoreName());
        assertThat( 36).isEqualTo(store.getMarketplaceId());
        assertThat( "WooCommerce").isEqualTo(store.getMarketplaceName());
        //assertThat( null).isEqualTo(store.getAccountName());
        //assertThat( null).isEqualTo(store.getEmail());
        assertThat( "http://shipstation-test.wpengine.com").isEqualTo(store.getIntegrationUrl());
        assertThat( true).isEqualTo(store.getActive());
        assertThat( "").isEqualTo(store.getCompanyName());
        assertThat( "").isEqualTo(store.getPhone());
        assertThat( "").isEqualTo(store.getPublicEmail());
        assertThat( "").isEqualTo(store.getWebsite());
        assertThat( "2014-12-16T17:47:05.457").isEqualTo(store.getRefreshDate());
        assertThat( "2014-12-16T09:47:05.457").isEqualTo(store.getLastRefreshAttempt());
        assertThat( "2014-11-06T15:21:13.223").isEqualTo(store.getCreateDate());
        assertThat( true).isEqualTo(store.getAutoRefresh());
        assertThat( Order.STATUS.awaiting_payment.toString()).isEqualTo(store.getStatusMappings().get(0).getOrderStatus());
        assertThat( "Pending").isEqualTo(store.getStatusMappings().get(0).getStatusKey());

    }

    @Test
    public void testUpdateStore() throws IOException, InterruptedException {

        Store storePayload = new Store();
        storePayload.setStoreId(12345L);
        storePayload.setStoreName("WooCommerce Store");
        //etc...

        Store store = api.updateStore(storePayload);

        assertThat( 12345).isEqualTo(store.getStoreId());
        assertThat( "WooCommerce Store").isEqualTo(store.getStoreName());
        assertThat( 36).isEqualTo(store.getMarketplaceId());
        assertThat( "WooCommerce").isEqualTo(store.getMarketplaceName());
        //assertThat( null).isEqualTo(store.getAccountName());
        //assertThat( null).isEqualTo(store.getEmail());
        assertThat( "http://shipstation-test.wpengine.com").isEqualTo(store.getIntegrationUrl());
        assertThat( true).isEqualTo(store.getActive());
        assertThat( "").isEqualTo(store.getCompanyName());
        assertThat( "").isEqualTo(store.getPhone());
        assertThat( "").isEqualTo(store.getPublicEmail());
        assertThat( "").isEqualTo(store.getWebsite());
        assertThat( "2014-12-16T17:47:05.457").isEqualTo(store.getRefreshDate());
        assertThat( "2014-12-16T09:47:05.457").isEqualTo(store.getLastRefreshAttempt());
        assertThat( "2014-11-06T15:21:13.223").isEqualTo(store.getCreateDate());
        assertThat( true).isEqualTo(store.getAutoRefresh());
        assertThat( Order.STATUS.awaiting_payment.toString()).isEqualTo(store.getStatusMappings().get(0).getOrderStatus());
        assertThat( "Pending").isEqualTo(store.getStatusMappings().get(0).getStatusKey());

    }

    @Test
    public void testGetStoreRefreshStatus() throws IOException, InterruptedException {

        StoreRefreshStatusResponse storeRefreshStatusResponse = api.getStoreRefreshStatus(12345);

        assertThat( 12345).isEqualTo(storeRefreshStatusResponse.getStoreId());
        assertThat( 2).isEqualTo(storeRefreshStatusResponse.getRefreshStatusId());
        assertThat( "Updating orders").isEqualTo(storeRefreshStatusResponse.getRefreshStatus());
        assertThat( "8-13-2014").isEqualTo(storeRefreshStatusResponse.getLastRefreshAttempt());
        assertThat( "8-13-2014").isEqualTo(storeRefreshStatusResponse.getRefreshDate());

    }

    @Test
    public void testRefreshStore() throws IOException, InterruptedException {

        SuccessResponse response = api.refreshStore(12345);

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "A store refresh has been initiated for Store ID 12345").isEqualTo(response.getMessage());

    }

    @Test
    public void testListStores() throws IOException, InterruptedException {

        List<Store> stores = api.listStores(false);

        assertThat( 22766).isEqualTo(stores.get(0).getStoreId());
        assertThat( "ShipStation Manual Store").isEqualTo(stores.get(0).getStoreName());
        assertThat( 0).isEqualTo(stores.get(0).getMarketplaceId());
        assertThat( "ShipStation").isEqualTo(stores.get(0).getMarketplaceName());
        //assertThat( null).isEqualTo(stores.get(0).getAccountName());
        //assertThat( null).isEqualTo(stores.get(0).getEmail());
        //assertThat( null).isEqualTo(stores.get(0).getIntegrationUrl());
        assertThat( true).isEqualTo(stores.get(0).getActive());
        assertThat( "").isEqualTo(stores.get(0).getPhone());
        assertThat( "testemail@email.com").isEqualTo(stores.get(0).getPublicEmail());
        assertThat( "").isEqualTo(stores.get(0).getWebsite());
        assertThat( "2014-12-03T11:46:11.283").isEqualTo(stores.get(0).getRefreshDate());
        assertThat( "2014-12-03T11:46:53.433").isEqualTo(stores.get(0).getLastRefreshAttempt());
        assertThat( "2014-07-25T11:05:55.307").isEqualTo(stores.get(0).getCreateDate());
        assertThat( "2014-11-12T08:45:20.55").isEqualTo(stores.get(0).getModifyDate());
        assertThat( false).isEqualTo(stores.get(0).getAutoRefresh());

    }

    @Test
    public void testListMarketplaces() throws IOException, InterruptedException {

        List<Marketplace> marketplaces = api.listMarketplaces();

        assertThat( "3DCart").isEqualTo(marketplaces.get(0).getName());
        assertThat( 23).isEqualTo(marketplaces.get(0).getMarketplaceId());
        assertThat( true).isEqualTo(marketplaces.get(0).getCanRefresh());
        assertThat( true).isEqualTo(marketplaces.get(0).getSupportsCustomMappings());
        assertThat( false).isEqualTo(marketplaces.get(0).getSupportsCustomStatuses());
        assertThat( true).isEqualTo(marketplaces.get(0).getCanConfirmShipments());

    }

    @Test
    public void testDeactivateStore() throws IOException, InterruptedException {

        SuccessResponse response = api.deactivateStore(12345);

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "The requested store has been reactivated.").isEqualTo(response.getMessage());

    }

    @Test
    public void testReactivateStore() throws IOException, InterruptedException {

        SuccessResponse response = api.reactivateStore(12345);

        assertThat( true).isEqualTo(response.getSuccess());
        assertThat( "The requested store has been reactivated.").isEqualTo(response.getMessage());

    }

    /* Doesnt work correctly with mock api - should return array of users, not a single user */
    //@Test
    public void testListUsers() throws IOException, InterruptedException {

        List<User> users = api.listUsers(true);

        assertThat( "123456AB-ab12-3c4d-5e67-89f1abc1defa").isEqualTo(users.get(0).getUserId());
        assertThat( "SS-user1").isEqualTo(users.get(0).getUserName());
        assertThat( "Shipping Employee 1").isEqualTo(users.get(0).getName());
    }


    @Test
    public void testGetWarehouse() throws IOException, InterruptedException {

        Warehouse warehouse = api.getWarehouse(12345);

        assertThat( 12345).isEqualTo(warehouse.getWarehouseId());
        assertThat( "API Ship From Location").isEqualTo(warehouse.getWarehouseName());
        assertThat( "API Warehouse").isEqualTo(warehouse.getOriginAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getOriginAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getOriginAddress().getStreet1());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getStreet2());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getOriginAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getOriginAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getOriginAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getOriginAddress().getCountry());
        assertThat( "512-555-5555").isEqualTo(warehouse.getOriginAddress().getPhone());
        assertThat( true).isEqualTo(warehouse.getOriginAddress().getResidential());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getAddressVerified());

        assertThat( "API Ship From Location").isEqualTo(warehouse.getReturnAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getReturnAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getReturnAddress().getStreet1());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getStreet2());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getReturnAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getReturnAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getReturnAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getReturnAddress().getCountry());
        assertThat( "512-555-5555").isEqualTo(warehouse.getReturnAddress().getPhone());
        assertThat( false).isEqualTo(warehouse.getReturnAddress().getResidential());//null boolean is false
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getAddressVerified());

        assertThat( "2015-07-02T08:38:31.4870000").isEqualTo(warehouse.getCreateDate());
        assertThat( true).isEqualTo(warehouse.getIsDefault());

    }

    @Test
    public void testUpdateWarehouse() throws IOException, InterruptedException {

        Warehouse warehousePayload = new Warehouse();
        warehousePayload.setWarehouseId(12345L);
        warehousePayload.setWarehouseName("API Ship From Location");
        Warehouse warehouse = api.updateWarehouse(warehousePayload);

        assertThat( 12345).isEqualTo(warehouse.getWarehouseId());
        assertThat( "API Ship From Location").isEqualTo(warehouse.getWarehouseName());
        assertThat( "API Warehouse").isEqualTo(warehouse.getOriginAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getOriginAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getOriginAddress().getStreet1());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getStreet2());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getOriginAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getOriginAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getOriginAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getOriginAddress().getCountry());
        assertThat( "512-555-5555").isEqualTo(warehouse.getOriginAddress().getPhone());
        assertThat( true).isEqualTo(warehouse.getOriginAddress().getResidential());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getAddressVerified());

        assertThat( "API Ship From Location").isEqualTo(warehouse.getReturnAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getReturnAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getReturnAddress().getStreet1());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getStreet2());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getReturnAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getReturnAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getReturnAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getReturnAddress().getCountry());
        assertThat( "512-555-5555").isEqualTo(warehouse.getReturnAddress().getPhone());
        assertThat( false).isEqualTo(warehouse.getReturnAddress().getResidential());//null boolean is false
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getAddressVerified());

        assertThat( "2015-07-02T08:38:31.4870000").isEqualTo(warehouse.getCreateDate());
        assertThat( true).isEqualTo(warehouse.getIsDefault());

    }

    @Test
    public void testCreateWarehouse() throws IOException, InterruptedException {

        Warehouse warehousePayload = new Warehouse();
        warehousePayload.setWarehouseId(12345L);
        warehousePayload.setWarehouseName("API Ship From Location");
        Warehouse warehouse = api.createWarehouse(warehousePayload);

        assertThat( 12345).isEqualTo(warehouse.getWarehouseId());
        assertThat( "API Ship From Location").isEqualTo(warehouse.getWarehouseName());
        assertThat( "API Warehouse").isEqualTo(warehouse.getOriginAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getOriginAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getOriginAddress().getStreet1());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getStreet2());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getOriginAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getOriginAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getOriginAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getOriginAddress().getCountry());
        assertThat( "512-555-5555").isEqualTo(warehouse.getOriginAddress().getPhone());
        assertThat( true).isEqualTo(warehouse.getOriginAddress().getResidential());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getAddressVerified());

        assertThat( "API Ship From Location").isEqualTo(warehouse.getReturnAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getReturnAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getReturnAddress().getStreet1());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getStreet2());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getReturnAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getReturnAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getReturnAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getReturnAddress().getCountry());
        assertThat( "512-555-5555").isEqualTo(warehouse.getReturnAddress().getPhone());
        assertThat( false).isEqualTo(warehouse.getReturnAddress().getResidential());//null boolean is false
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getAddressVerified());

        assertThat( "2015-07-02T08:38:31.4870000").isEqualTo(warehouse.getCreateDate());
        assertThat( true).isEqualTo(warehouse.getIsDefault());
    }

    @Test
    public void testListWarehouse() throws IOException, InterruptedException {

        List<Warehouse> warehouses = api.listWarehouses();

        Warehouse warehouse = warehouses.get(1);
        assertThat( 14265).isEqualTo(warehouse.getWarehouseId());
        assertThat( "Austin").isEqualTo(warehouse.getWarehouseName());
        assertThat( "Austin").isEqualTo(warehouse.getOriginAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getOriginAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getOriginAddress().getStreet1());
        assertThat( "").isEqualTo(warehouse.getOriginAddress().getStreet2());
        assertThat( "").isEqualTo(warehouse.getOriginAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getOriginAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getOriginAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getOriginAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getOriginAddress().getCountry());
        assertThat( "5124445555").isEqualTo(warehouse.getOriginAddress().getPhone());
        assertThat( false).isEqualTo(warehouse.getOriginAddress().getResidential());
        //assertThat( null).isEqualTo(warehouse.getOriginAddress().getAddressVerified());

        assertThat( "ShipStation").isEqualTo(warehouse.getReturnAddress().getName());
        assertThat( "ShipStation").isEqualTo(warehouse.getReturnAddress().getCompany());
        assertThat( "2815 Exposition Blvd").isEqualTo(warehouse.getReturnAddress().getStreet1());
        assertThat( "").isEqualTo(warehouse.getReturnAddress().getStreet2());
        assertThat( "").isEqualTo(warehouse.getReturnAddress().getStreet3());
        assertThat( "Austin").isEqualTo(warehouse.getReturnAddress().getCity());
        assertThat( "TX").isEqualTo(warehouse.getReturnAddress().getState());
        assertThat( "78703").isEqualTo(warehouse.getReturnAddress().getPostalCode());
        assertThat( "US").isEqualTo(warehouse.getReturnAddress().getCountry());
        assertThat( "5124445555").isEqualTo(warehouse.getReturnAddress().getPhone());
        assertThat( false).isEqualTo(warehouse.getReturnAddress().getResidential());
        //assertThat( null).isEqualTo(warehouse.getReturnAddress().getAddressVerified());

        assertThat( "2014-05-27T09:54:29.9600000").isEqualTo(warehouse.getCreateDate());
        assertThat( false).isEqualTo(warehouse.getIsDefault());

    }

    @Test
    public void testListWebhooks() throws IOException, InterruptedException {

        ListWebhooks webhooks = api.listWebhooks();

        assertThat( false).isEqualTo(webhooks.getWebhooks().get(0).getLabelApiHook());
        assertThat( 123).isEqualTo(webhooks.getWebhooks().get(0).getWebHookId());
        assertThat( 100000).isEqualTo(webhooks.getWebhooks().get(0).getSellerId());
        assertThat( "ITEM_ORDER_NOTIFY").isEqualTo(webhooks.getWebhooks().get(0).getHookType());
        assertThat( "Json").isEqualTo(webhooks.getWebhooks().get(0).getMessageFormat());
        assertThat( "http://example.endpoint/orders").isEqualTo(webhooks.getWebhooks().get(0).getUrl());
        assertThat( "My Order Webhook").isEqualTo(webhooks.getWebhooks().get(0).getName());
        //assertThat( null).isEqualTo(webhooks.getWebhooks().get(0).getBulkCopyBatchID());
        //assertThat( null).isEqualTo(webhooks.getWebhooks().get(0).getBulkCopyBatchID());
        assertThat( true).isEqualTo(webhooks.getWebhooks().get(0).getActive());
        assertThat( new ArrayList<String>()).isEqualTo(webhooks.getWebhooks().get(0).getWebhookLogs());
        //assertThat( null).isEqualTo(webhooks.getWebhooks().get(0).getSeller());
        //assertThat( null).isEqualTo(webhooks.getWebhooks().get(0).getStore());
    }

    @Test
    public void testSubscribeToWebhook() throws IOException, InterruptedException {

        SubscribeWebhookPayload payload = new SubscribeWebhookPayload();
        payload.setTarget_url("http://someexamplewebhookurl.com/neworder");
        payload.setEvent("ORDER_NOTIFY");
        //etc...

        Integer id = api.subscribeToWebhook(payload);

        assertThat( 123456).isEqualTo(id);
    }

    @Test
    public void testUnsubscribeToWebhook() throws IOException, InterruptedException {

        api.unsubscribeToWebhook(123456);

    }
}
