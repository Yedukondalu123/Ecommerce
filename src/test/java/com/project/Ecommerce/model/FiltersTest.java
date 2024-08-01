package com.project.Ecommerce.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {

    @Test
    void testGettersAndSetters() {
        // Prepare test data
        String orderId = "123";
        String customer = "John Doe";
        String orderItem = "Product X";
        String deliveryDate = "2023-07-28";
        String deliveryPricing = "100-200";
        List<String> status = Arrays.asList("Completed", "Pending");

        // Create Filters object
        Filters filters = new Filters();

        // Set values
        filters.setOrderId(orderId);
        filters.setCustomer(customer);
        filters.setOrderItem(orderItem);
        filters.setDeliveryDate(deliveryDate);
        filters.setDeliveryPricing(deliveryPricing);
        filters.setStatus(status);

        // Assert values
        assertEquals(orderId, filters.getOrderId());
        assertEquals(customer, filters.getCustomer());
        assertEquals(orderItem, filters.getOrderItem());
        assertEquals(deliveryDate, filters.getDeliveryDate());
        assertEquals(deliveryPricing, filters.getDeliveryPricing());
        assertEquals(status, filters.getStatus());
    }

    @Test
    void testAllArgsConstructor() {
        // Prepare test data
        String orderId = "123";
        String customer = "John Doe";
        String orderItem = "Product X";
        String deliveryDate = "2023-07-28";
        String deliveryPricing = "100-200";
        List<String> status = Arrays.asList("Completed", "Pending");

        // Create Filters object using all-args constructor
        Filters filters = new Filters(orderId, customer, orderItem, deliveryDate, deliveryPricing, status);

        // Assert values
        assertEquals(orderId, filters.getOrderId());
        assertEquals(customer, filters.getCustomer());
        assertEquals(orderItem, filters.getOrderItem());
        assertEquals(deliveryDate, filters.getDeliveryDate());
        assertEquals(deliveryPricing, filters.getDeliveryPricing());
        assertEquals(status, filters.getStatus());
    }

    @Test
    void testNoArgsConstructor() {
        // Create Filters object using no-args constructor
        Filters filters = new Filters();

        // Assert default values
        assertNull(filters.getOrderId());
        assertNull(filters.getCustomer());
        assertNull(filters.getOrderItem());
        assertNull(filters.getDeliveryDate());
        assertNull(filters.getDeliveryPricing());
        assertNull(filters.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prepare test data
        String orderId = "123";
        String customer = "John Doe";
        String orderItem = "Product X";
        String deliveryDate = "2023-07-28";
        String deliveryPricing = "100-200";
        List<String> status = Arrays.asList("Completed", "Pending");

        // Create Filters objects
        Filters filters1 = new Filters(orderId, customer, orderItem, deliveryDate, deliveryPricing, status);
        Filters filters2 = new Filters(orderId, customer, orderItem, deliveryDate, deliveryPricing, status);

        // Assert equality and hash code
        assertEquals(filters1, filters2);
        assertEquals(filters1.hashCode(), filters2.hashCode());
    }

    @Test
    void testToString() {
        // Prepare test data
        String orderId = "123";
        String customer = "John Doe";
        String orderItem = "Product X";
        String deliveryDate = "2023-07-28";
        String deliveryPricing = "100-200";
        List<String> status = Arrays.asList("Completed", "Pending");

        // Create Filters object
        Filters filters = new Filters(orderId, customer, orderItem, deliveryDate, deliveryPricing, status);

        // Assert toString method
        String expectedToString = "Filters(orderId=123, customer=John Doe, orderItem=Product X, deliveryDate=2023-07-28, deliveryPricing=100-200, status=[Completed, Pending])";
        assertEquals(expectedToString, filters.toString());
    }
}
