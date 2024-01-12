package com.apex.shipstation;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.MapperFeature;

import com.apex.shipstation.model.*;

public class API {

    private String apiBaseURL;
    private String apiKey;
    private String apiSecret;
    private JsonMapper mapper;
    private HttpClient client;

    public API(String BaseURL, String key, String secret) {
        apiBaseURL = BaseURL;
        apiKey = key;
        apiSecret = secret;
        mapper = JsonMapper.builder()
          .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
          .build();
        client = HttpClient.newHttpClient();
    }

    private boolean checkRateLimit(HttpResponse response) throws InterruptedException {

        if (response.statusCode() == 429) {

            // http://docs.shipstation.apiary.io/#introduction/shipstation-api-requirements/api-rate-limits
            long sec = 40;// default reset time 40 seconds

            // parse header that defines how long to wait
            Map<String,List<String>> headers = response.headers().map();
            if (headers.containsKey("X-Rate-Limit-Reset")) {
                sec = Long.parseLong(headers.get("X-Rate-Limit-Reset").get(0));
            }
            System.out.println("ShipStation API got (429)- Sleeping for " + (sec + 1) + " seconds");

            Thread.sleep((sec + 1) * 1000); // sleep

            return true; // wait occurred - retry request!
        }
        return false; // no wait - continue!
    }

    private String authHeader() {
        String auth = apiKey + ":" + apiSecret;
        String authB64 = Base64.getEncoder().encodeToString(auth.getBytes());

        return String.format("Basic %s", authB64);
    }

    private HttpResponse<String> POST(String api_url, String jsonPayload) throws IOException, InterruptedException {

        boolean retry = true;
        HttpResponse<String> response = null;

        do {

            // HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(api_url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authHeader())
                    .POST(BodyPublishers.ofString(jsonPayload))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // check if we need to wait
            if (Objects.nonNull(response)) {
                retry = checkRateLimit(response);
            }

        } while (retry);// retry if we hit a rate limit

        return response;
    }

    private HttpResponse<String> GET(String api_url) throws IOException, InterruptedException {

        boolean retry = true;
        HttpResponse<String> response = null;

        do {

            // HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseURL + api_url))
                    //.header("Content-Type", "text/plain")
                    .header("Authorization", authHeader())
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // check if we need to wait
            if (Objects.nonNull(response)) {
                retry = checkRateLimit(response);
            }

        } while (retry);// retry if we hit a rate limit

        return response;
    }

    private HttpResponse<String> DELETE(String api_url) throws IOException, InterruptedException {

        boolean retry = true;
        HttpResponse<String> response = null;

        do {

            // HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseURL + api_url))
                    //.header("Content-Type", "text/plain")
                    .header("Authorization", authHeader())
                    .DELETE()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // check if we need to wait
            if (Objects.nonNull(response)) {
                retry = checkRateLimit(response);
            }

        } while (retry);// retry if we hit a rate limit

        return response;
    }

    private HttpResponse<String> PUT(String api_url, String jsonPayload) throws IOException, InterruptedException {

        boolean retry = true;
        HttpResponse<String> response = null;

        do {

            // HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseURL + api_url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authHeader())
                    .PUT(BodyPublishers.ofString(jsonPayload))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // check if we need to wait
            if (Objects.nonNull(response)) {
                retry = checkRateLimit(response);
            }

        } while (retry);// retry if we hit a rate limit

        return response;
    }

    public List<Tag> listTags() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/accounts/listtags");
        return mapper.readValue(res.body(), new TypeReference<List<Tag>>() {
        });
    }

    public List<Carrier> listCarriers() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/carriers");
        return mapper.readValue(res.body(), new TypeReference<List<Carrier>>() {
        });
    }

    public Carrier getCarrier(String carrierCode) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/carriers/getcarrier?carrierCode=" + carrierCode);
        return mapper.readValue(res.body(), Carrier.class);
    }

    public Carrier addFunds(Fund fund) throws IOException, InterruptedException, JsonProcessingException {
        String payload = mapper.writeValueAsString(fund);
        HttpResponse<String> res = POST("/carriers/addfunds", payload);
        return mapper.readValue(res.body(), Carrier.class);
    }


    public List<com.apex.shipstation.model.Package> listPackages(String carrierCode) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/carriers/listpackages?carrierCode=" + carrierCode);
        return mapper.readValue(res.body(), new TypeReference<List<com.apex.shipstation.model.Package>>() {
        });
    }

    public List<Service> listServices(String carrierCode) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/carriers/listservices?carrierCode=" + carrierCode);
        return mapper.readValue(res.body(), new TypeReference<List<Service>>() {
        });
    }

    public Customer getCustomer(String customerId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/customers/" + customerId);
        return mapper.readValue(res.body(), Customer.class);
    }

    public ListCustomers listCustomers() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/customers");
        return mapper.readValue(res.body(), ListCustomers.class);
    }

    public ListFulfillments listFulfillments() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/fulfillments");
        return mapper.readValue(res.body(), ListFulfillments.class);
    }

    public ListFulfillments listFulfillments(String query) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/fulfillments?" + query);
        return mapper.readValue(res.body(), ListFulfillments.class);
    }

    public Order getOrder(long orderId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/orders/" + orderId);
        return mapper.readValue(res.body(), Order.class);
    }

    public SuccessResponse deleteOrder(long orderId) throws IOException, InterruptedException {
        HttpResponse<String> res = DELETE("/orders/" + orderId);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public SuccessResponse addTagToOrder(long orderId, long tagId) throws IOException, InterruptedException {
        String payload = "{ 'orderId':" + orderId + ", 'tagId':" + tagId + "}";
        HttpResponse<String> res = POST("/orders/addtag", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public SuccessResponse assignUserToOrder(UserToOrderPayload assignusertoorder) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(assignusertoorder);
        HttpResponse<String> res = POST("/orders/assignuser", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public Label createLabelForOrder(LabelForOrderPayload orderPayload) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(orderPayload);
        HttpResponse<String> res = POST("/orders/createlabelfororder", payload);
        return mapper.readValue(res.body(), Label.class);
    }

    public Order createOrder(Order order) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(order);
        HttpResponse<String> res = POST("/orders/createorder", payload);
        return mapper.readValue(res.body(), Order.class);
    }

    public BulkOrdersResponse createOrders(List<Order> orders) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(orders);
        HttpResponse<String> res = POST("/orders/createorders", payload);
        return mapper.readValue(res.body(), BulkOrdersResponse.class);
    }

    public SuccessResponse holdOrderUntil(long orderId, String date) throws IOException, InterruptedException {
        String payload = "{ 'orderId':" + orderId + ", 'holdUntilDate' : '" + date + "'}";
        HttpResponse<String> res = POST("/orders/holduntil", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    private Boolean emptyOrNullString(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }

    private ListOrders listOrders(String q, Integer p) throws IOException, InterruptedException {
        Integer page = Objects.isNull(p) ? 1 : p;
        String pageQuery = String.format("page=%d", page);
        String query = emptyOrNullString(q) ? pageQuery : q + "&" + pageQuery;
        query = !query.startsWith("?") ? "?" + query : query;
        HttpResponse<String> res = GET("/orders" + query);
        return mapper.readValue(res.body(), ListOrders.class);
    }

    public ListOrders listOrders(String query) throws IOException, InterruptedException {
        ListOrders firstPage = listOrders(query, null);
        ListOrders allPages = new ListOrders();
        List<Order> orderList = firstPage.getOrders();
        Integer numPages = firstPage.getPages();
        for (Integer page = 2; page <= numPages; ++page) {
            orderList.addAll(listOrders(query, page).getOrders());
        }
        allPages.setOrders(orderList);

        return allPages;
    }

    public ListOrders listOrders() throws IOException, InterruptedException {
        return listOrders(null);
    }

    public ListOrders listOrdersByStatus(Order.STATUS status) throws IOException, InterruptedException {
        return listOrders("?orderStatus=" + status.toString());
    }

    public ListOrders listOrdersByTag(String query) throws IOException, InterruptedException {
        return listOrders("/listbytag?" + query);
    }

    public OrderAsShippedResponse markAnOrderAsShipped(OrderAsShippedPayload orderAsShippedPayload) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(orderAsShippedPayload);
        HttpResponse<String> res = POST("/orders/markasshipped", payload);
        return mapper.readValue(res.body(), OrderAsShippedResponse.class);
    }

    public SuccessResponse removeTagFromOrder(long orderId, long tagId) throws IOException, InterruptedException {
        String payload = "{ 'orderId':" + orderId + ", 'tagId':" + tagId + "}";
        HttpResponse<String> res = POST("/orders/removetag", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public SuccessResponse restoreOrderFromOnHold(long orderId) throws IOException, InterruptedException {
        String payload = "{ 'orderId':" + orderId + "}";
        HttpResponse<String> res = POST("/orders/restorefromhold", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public SuccessResponse unassignUserFromOrder(List<String> orderIds) throws IOException, InterruptedException {
        String payload = "{ 'orderIds':" + String.join(",", orderIds + "}");
        HttpResponse<String> res = POST("/orders/unassignuser", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public Product getProduct(long productId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/products/" + productId);
        return mapper.readValue(res.body(), Product.class);
    }

    public SuccessResponse updateProduct(Product product) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(product);
        HttpResponse<String> res = PUT("/products/" + product.getProductId(), payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public ListProducts listProducts() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/products");
        return mapper.readValue(res.body(), ListProducts.class);
    }

    public ListProducts listProducts(String query) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/products?" + query);
        return mapper.readValue(res.body(), ListProducts.class);
    }

    private ListShipments listShipments(String q, Integer p) throws IOException, InterruptedException {
        Integer page = Objects.isNull(p) ? 1 : p;
        String pageQuery = String.format("page=%d", p);
        String query = emptyOrNullString(q) ? pageQuery : q + "&" + pageQuery;
        query = !query.startsWith("?") ? "?" + query : query;
        HttpResponse<String> res = GET("/shipments" + query);
        return mapper.readValue(res.body(), ListShipments.class);
    }

    public ListShipments listShipments(String query) throws IOException, InterruptedException {
        ListShipments firstPage = listShipments(query, null);
        ListShipments allPages = new ListShipments();
        List<Shipment> shipmentList = firstPage.getShipments();
        Integer numPages = firstPage.getPages();
        for (Integer page = 2; page <= numPages; ++page) {
            shipmentList.addAll(listShipments(query, page).getShipments());
        }
        allPages.setShipments(shipmentList);

        return allPages;
    }

    public ListShipments listShipments() throws IOException, InterruptedException {
        return listShipments(null);
    }

    public Shipment createShipmentLabel(ShipmentLabelPayload shipmentLabelPayload) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(shipmentLabelPayload);
        HttpResponse<String> res = POST("/shipments/createlabel", payload);
        return mapper.readValue(res.body(), Shipment.class);
    }

    public List<Rate> getRates(RatePayload rate) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(rate);
        HttpResponse<String> res = POST("/shipments/getrates", payload);
        return mapper.readValue(res.body(), new TypeReference<List<Rate>>() {
        });
    }

    public VoidLabelResponse voidLabel(long shipmentId) throws IOException, InterruptedException {
        String payload = "{ 'shipmentId':" + shipmentId + "}";
        HttpResponse<String> res = POST("/shipments/voidlabel", payload);
        return mapper.readValue(res.body(), VoidLabelResponse.class);
    }

    public Store getStore(long storeId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/stores/" + storeId);
        return mapper.readValue(res.body(), Store.class);
    }

    public Store updateStore(Store store) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(store);
        HttpResponse<String> res = PUT("/stores/" + store.getStoreId(), payload);
        return mapper.readValue(res.body(), Store.class);
    }

    public StoreRefreshStatusResponse getStoreRefreshStatus(long storeId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/stores/getrefreshstatus?storeId=" + storeId);
        return mapper.readValue(res.body(), StoreRefreshStatusResponse.class);
    }

    public SuccessResponse refreshStore(long storeId) throws IOException, InterruptedException {
        String payload = "{ ''storeId'':" + storeId + "}";
        HttpResponse<String> res = POST("/stores/refreshstore", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public List<Store> listStores(boolean showInactive, long marketplaceId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/stores?showInactive={showInactive}?marketplaceId={marketplaceId}");
        return mapper.readValue(res.body(), new TypeReference<List<Store>>() {
        });
    }

    public List<Store> listStores(boolean showInactive) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/stores?showInactive=" + (showInactive ? "true" : "false"));
        return mapper.readValue(res.body(), new TypeReference<List<Store>>() {
        });
    }

    public List<Marketplace> listMarketplaces() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/stores/marketplaces");
        return mapper.readValue(res.body(), new TypeReference<List<Marketplace>>() {
        });
    }

    public SuccessResponse deactivateStore(long storeId) throws IOException, InterruptedException {
        String payload = "{ 'storeId':" + storeId + "}";
        HttpResponse<String> res = POST("/stores/deactivate", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public SuccessResponse reactivateStore(long storeId) throws IOException, InterruptedException {
        String payload = "{ 'storeId':" + storeId + "}";
        HttpResponse<String> res = POST("/stores/reactivate", payload);
        return mapper.readValue(res.body(), SuccessResponse.class);
    }

    public List<User> listUsers(boolean showInactive) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/users?showInactive=" + (showInactive ? "true" : "false"));
        return mapper.readValue(res.body(), new TypeReference<List<User>>() {
        });
    }

    public Warehouse getWarehouse(long warehouseId) throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/warehouses/" + warehouseId);
        return mapper.readValue(res.body(), Warehouse.class);
    }

    public Warehouse updateWarehouse(Warehouse warehouse) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(warehouse);
        HttpResponse<String> res = PUT("/warehouses/" + warehouse.getWarehouseId(), payload);
        return mapper.readValue(res.body(), Warehouse.class);
    }

    public Warehouse createWarehouse(Warehouse warehouse) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(warehouse);
        HttpResponse<String> res = PUT("/warehouses/createwarehouse", payload);
        return mapper.readValue(res.body(), Warehouse.class);
    }

    public List<Warehouse> listWarehouses() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/warehouses");
        return mapper.readValue(res.body(), new TypeReference<List<Warehouse>>() {
        });
    }

    // Doesnt work due to uppercase json keys from api
    public ListWebhooks listWebhooks() throws IOException, InterruptedException {
        HttpResponse<String> res = GET("/webhooks");
        return mapper.readValue(res.body(), ListWebhooks.class);
    }

    public int subscribeToWebhook(SubscribeWebhookPayload subscribeWebhookPayload) throws IOException, InterruptedException {
        String payload = mapper.writeValueAsString(subscribeWebhookPayload);
        HttpResponse<String> res = POST("/webhooks/subscribe", payload);
        return mapper.readTree(res.body()).path("id").asInt();
    }

    public void unsubscribeToWebhook(long webhookId) throws IOException, InterruptedException {
        HttpResponse<String> res = DELETE("/webhooks/webhookId/" + webhookId);
    }
}
