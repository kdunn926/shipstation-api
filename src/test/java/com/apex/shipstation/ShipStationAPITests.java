package com.apex.shipstation;

import java.util.ArrayList;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.apex.shipstation.model.Address;
import com.apex.shipstation.model.Order;
import com.apex.shipstation.model.OrderItem;

public class ShipStationAPITests {

    @Mock
    private Client mockClient;
    
    @Mock
    private WebTarget mockWebTarget;
    
    @Mock
    private Invocation.Builder mockInvocationBuilder;
    
    @Mock
    private Response mockResponse;

    private API shipStationService;
    private ObjectMapper objectMapper;
    private MockedStatic<ClientBuilder> mockClientBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        objectMapper = new ObjectMapper();
        shipStationService = new API("http://ship.station", "key", "secret");

        // Mock ClientBuilder.newClient() to return our mock client
        mockClientBuilder = mockStatic(ClientBuilder.class);
        mockClientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
        
        // Setup mock chain
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.request(eq(MediaType.APPLICATION_JSON_TYPE))).thenReturn(mockInvocationBuilder);
        when(mockInvocationBuilder.header(anyString(), anyString())).thenReturn(mockInvocationBuilder);
    }
    
    @AfterEach
    public void tearDown() {
        if (mockClientBuilder != null) {
            mockClientBuilder.close();
        }
    }

    private Order makeTestOrder() {
        Order order = new Order();
        order.setOrderNumber("ORD-001");
        //order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(Order.STATUS.awaiting_shipment);
        order.setCustomerUsername("JDoe");
        order.setCustomerEmail("john.doe@example.com");

        Address shippingAddress = new Address();
        shippingAddress.setName("John Doe");
        shippingAddress.setStreet1("123 Main St");
        shippingAddress.setCity("Anytown");
        shippingAddress.setState("CA");
        shippingAddress.setPostalCode("12345");
        shippingAddress.setCountry("US");
        order.setShipTo(shippingAddress);

        Address billTo = new Address();
        billTo.setName("John Doe");
        billTo.setStreet1("123 Main St");
        billTo.setCity("Anytown");
        billTo.setState("CA");
        billTo.setPostalCode("12345");
        billTo.setCountry("US");
        order.setBillTo(billTo);

        OrderItem orderItem = new OrderItem();
        orderItem.setSku("ITEM-001");
        orderItem.setName("Test Item");
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(25.99);

        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        order.setItems(items);

        order.setAmountPaid(51.98);
        order.setTaxAmount(4.16);
        order.setShippingAmount(5.00);
        order.setCarrierCode("ups");
        order.setServiceCode("ground");

        return order;
    }

    @Test
    void testUpdateOrderNoShippingFields() throws Exception {
        // Arrange
        when(mockInvocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.readEntity(String.class)).thenReturn("{\"orderId\": 12345}");

        // Act
        shipStationService.createOrUpdateOrder(makeTestOrder(), true);

        // Assert
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
        verify(mockInvocationBuilder).post(entityCaptor.capture());

        Entity<?> capturedEntity = entityCaptor.getValue();
        assertNotNull(capturedEntity);
        assertEquals(MediaType.APPLICATION_JSON_TYPE, capturedEntity.getMediaType());

        // Validate JSON structure
        String jsonPayload = (String) capturedEntity.getEntity();
        JsonNode jsonNode = objectMapper.readTree(jsonPayload);

        // Assert shipping fields are omitted
        assertFalse(jsonNode.has("carrierCode"));
        assertFalse(jsonNode.has("serviceCode"));
    }

    @Test
    void testCreateOrderJsonStructure() throws Exception {
        // Arrange
        when(mockInvocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.readEntity(String.class)).thenReturn("{\"orderId\": 12345}");

        // Act
        shipStationService.createOrder(makeTestOrder());

        // Assert
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
        verify(mockInvocationBuilder).post(entityCaptor.capture());

        Entity<?> capturedEntity = entityCaptor.getValue();
        assertNotNull(capturedEntity);
        assertEquals(MediaType.APPLICATION_JSON_TYPE, capturedEntity.getMediaType());

        // Validate JSON structure
        String jsonPayload = (String) capturedEntity.getEntity();
        JsonNode jsonNode = objectMapper.readTree(jsonPayload);

        // Assert required fields exist
        assertTrue(jsonNode.has("orderNumber"));
        //assertTrue(jsonNode.has("orderDate"));
        assertTrue(jsonNode.has("orderStatus"));
        assertTrue(jsonNode.has("customerUsername"));
        assertTrue(jsonNode.has("customerEmail"));
        assertTrue(jsonNode.has("shipTo"));
        assertTrue(jsonNode.has("billTo"));
        assertTrue(jsonNode.has("items"));
        assertTrue(jsonNode.has("amountPaid"));

        // Assert address structure
        JsonNode shipTo = jsonNode.get("shipTo");
        assertTrue(shipTo.has("name"));
        assertTrue(shipTo.has("street1"));
        assertTrue(shipTo.has("city"));
        assertTrue(shipTo.has("state"));
        assertTrue(shipTo.has("postalCode"));
        assertTrue(shipTo.has("country"));

        // Assert items structure
        JsonNode responseItems = jsonNode.get("items");
        assertTrue(responseItems.isArray());
        assertTrue(responseItems.size() > 0);
        
        JsonNode firstItem = responseItems.get(0);
        assertTrue(firstItem.has("sku"));
        assertTrue(firstItem.has("name"));
        assertTrue(firstItem.has("quantity"));
        assertTrue(firstItem.has("unitPrice"));

        // Verify values
        assertEquals("ORD-001", jsonNode.get("orderNumber").asText());
        assertEquals("awaiting_shipment", jsonNode.get("orderStatus").asText());
        assertEquals("JDoe", jsonNode.get("customerUsername").asText());
        assertEquals(2, firstItem.get("quantity").asInt());
    }

    /*
    @Test
    void testGetOrderJsonResponse() throws Exception {
        // Arrange
        String mockResponseJson = """
            {
                "orderId": 12345,
                "orderNumber": "ORD-001",
                "orderStatus": "shipped",
                "customerName": "John Doe",
                "shipTo": {
                    "name": "John Doe",
                    "street1": "123 Main St",
                    "city": "Anytown",
                    "state": "CA",
                    "postalCode": "12345",
                    "country": "US"
                },
                "items": [
                    {
                        "sku": "ITEM-001",
                        "name": "Test Item",
                        "quantity": 2,
                        "unitPrice": 25.99
                    }
                ]
            }
            """;

        when(mockInvocationBuilder.get()).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.readEntity(String.class)).thenReturn(mockResponseJson);

        // Act
        OrderResponse result = shipStationService.getOrder("12345");

        // Assert
        assertNotNull(result);
        assertEquals(12345L, result.getOrderId());
        assertEquals("ORD-001", result.getOrderNumber());
        assertEquals("shipped", result.getOrderStatus());
        assertEquals("John Doe", result.getCustomerName());
        
        assertNotNull(result.getShipTo());
        assertEquals("John Doe", result.getShipTo().getName());
        assertEquals("123 Main St", result.getShipTo().getStreet1());
        
        assertNotNull(result.getItems());
        assertEquals(1, result.getItems().size());
        assertEquals("ITEM-001", result.getItems().get(0).getSku());
        assertEquals(2, result.getItems().get(0).getQuantity());
    }

    @Test
    void testUpdateOrderStatusJsonStructure() throws Exception {
        // Arrange
        when(mockInvocationBuilder.put(any(Entity.class))).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(200);

        UpdateOrderStatusRequest updateRequest = UpdateOrderStatusRequest.builder()
            .orderId(12345L)
            .orderStatus("shipped")
            .carrierCode("fedex")
            .trackingNumber("1234567890")
            .build();

        // Act
        shipStationService.updateOrderStatus(updateRequest);

        // Assert
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
        verify(mockInvocationBuilder).put(entityCaptor.capture());

        Entity<?> capturedEntity = entityCaptor.getValue();
        String jsonPayload = objectMapper.writeValueAsString(capturedEntity.getEntity());
        JsonNode jsonNode = objectMapper.readTree(jsonPayload);

        assertTrue(jsonNode.has("orderId"));
        assertTrue(jsonNode.has("orderStatus"));
        assertTrue(jsonNode.has("carrierCode"));
        assertTrue(jsonNode.has("trackingNumber"));

        assertEquals(12345, jsonNode.get("orderId").asLong());
        assertEquals("shipped", jsonNode.get("orderStatus").asText());
        assertEquals("fedex", jsonNode.get("carrierCode").asText());
        assertEquals("1234567890", jsonNode.get("trackingNumber").asText());
    }

    @Test
    void testCreateShipmentJsonStructure() throws Exception {
        // Arrange
        when(mockInvocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(201);
        when(mockResponse.readEntity(String.class)).thenReturn("{\"shipmentId\": 98765}");

        CreateShipmentRequest shipmentRequest = CreateShipmentRequest.builder()
            .orderId(12345L)
            .carrierCode("ups")
            .serviceCode("ups_ground")
            .packageCode("package")
            .weight(Weight.builder()
                .value(2.5)
                .units("pounds")
                .build())
            .dimensions(Dimensions.builder()
                .length(12.0)
                .width(8.0)
                .height(6.0)
                .units("inches")
                .build())
            .build();

        // Act
        shipStationService.createShipment(shipmentRequest);

        // Assert
        ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
        verify(mockInvocationBuilder).post(entityCaptor.capture());

        Entity<?> capturedEntity = entityCaptor.getValue();
        String jsonPayload = objectMapper.writeValueAsString(capturedEntity.getEntity());
        JsonNode jsonNode = objectMapper.readTree(jsonPayload);

        assertTrue(jsonNode.has("orderId"));
        assertTrue(jsonNode.has("carrierCode"));
        assertTrue(jsonNode.has("serviceCode"));
        assertTrue(jsonNode.has("packageCode"));
        assertTrue(jsonNode.has("weight"));
        assertTrue(jsonNode.has("dimensions"));

        JsonNode weight = jsonNode.get("weight");
        assertTrue(weight.has("value"));
        assertTrue(weight.has("units"));
        assertEquals(2.5, weight.get("value").asDouble());
        assertEquals("pounds", weight.get("units").asText());

        JsonNode dimensions = jsonNode.get("dimensions");
        assertTrue(dimensions.has("length"));
        assertTrue(dimensions.has("width"));
        assertTrue(dimensions.has("height"));
        assertTrue(dimensions.has("units"));
    }
    */

    // Helper method to verify authentication headers
    @Test
    void testAuthenticationHeaders() throws Exception {
        // Arrange
        when(mockInvocationBuilder.post(any(Entity.class))).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.readEntity(String.class)).thenReturn("{}");

        Order order = new Order();
        order.setOrderId(140335319);
        order.setOrderNumber("TEST-ORDER-API-DOCS");

        // Act
        shipStationService.createOrder(order);

        // Assert - verify content type
        verify(mockWebTarget).request(eq(MediaType.APPLICATION_JSON_TYPE));

        // Assert - verify authentication header was set
        verify(mockInvocationBuilder).header(eq("Authorization"), anyString());
    }
}
