package com.pizzashack.client.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzashack.client.dto.Order;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private HTTPClient httpClient;
    private String serverURL;
    private final String PIZZA_ORDER_URL = "/1.0.0/order";
    private final String PIZZA_DELIVERY_URL = "/1.0.0/delivery";


    public OrderManager() {
        httpClient = new HTTPClient();
        serverURL = PizzaShackWebConfiguration.getInstance().getServerURL();
    }

    public Order saveOrder(String address, String pizzaType, String quantity
            , String customerName, String creditCardNumber, String token) {
        Order order = new Order(address,pizzaType,customerName,quantity,creditCardNumber);
        String jsonString = JSONClient.generateSaveOrderMessage(order);

        String submitUrl = PizzaShackWebConfiguration.getInstance().getServerURL() + PIZZA_ORDER_URL;
        try {
            HttpResponse httpResponse = httpClient.doPost(submitUrl,"Bearer " + token,jsonString,"application/json");
            String response = httpClient.getResponsePayload(httpResponse);
            //new instance with orderId
            order = JSONClient.getOrder(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
    }

    public Order getOrder(String orderId, String token){
        String submitUrl = PizzaShackWebConfiguration.getInstance().getServerURL()
                + PIZZA_ORDER_URL + "/" + orderId;

        Order order = null;
        try {
            HttpResponse httpResponse = httpClient.doGet(submitUrl, "Bearer " + token);
            String response = httpClient.getResponsePayload(httpResponse);
            order = JSONClient.getOrder(response);
            return order;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<Order> getAllOrders(String token) {
        String submitUrl = PizzaShackWebConfiguration.getInstance().getServerURL()
                + PIZZA_DELIVERY_URL;

        List<Order> orders = null;
        try {
            HttpResponse httpResponse = httpClient.doGet(submitUrl, "Bearer " + token);
            String response = httpClient.getResponsePayload(httpResponse);
            ObjectMapper objectMapper = new ObjectMapper();
            orders = objectMapper.readValue(response, new TypeReference<ArrayList<Order>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
