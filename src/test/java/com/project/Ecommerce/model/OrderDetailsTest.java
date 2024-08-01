package com.project.Ecommerce.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

public class OrderDetailsTest {

    @Test
    public void testConstructor() {
        OrderDetails orderDetails = OrderDetails.builder()
                .orderId("ORD123")
                .customer("John Doe")
                .order("Smartphone")
                .deliveryDate(Date.valueOf("2024-07-01"))
                .deliveryPricing(699.99)
                .deliveryStatus("Pending")
                .build();

        assertEquals("ORD123", orderDetails.getOrderId());
        assertEquals("John Doe", orderDetails.getCustomer());
        assertEquals("Smartphone", orderDetails.getOrder());
        assertEquals(Date.valueOf("2024-07-01"), orderDetails.getDeliveryDate());
        assertEquals(699.99, orderDetails.getDeliveryPricing());
        assertEquals("Pending", orderDetails.getDeliveryStatus());
    }

    @Test
    public void testSetters() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId("ORD124");
        orderDetails.setCustomer("Jane Doe");
        orderDetails.setOrder("Laptop");
        orderDetails.setDeliveryDate(Date.valueOf("2024-07-02"));
        orderDetails.setDeliveryPricing(999.99);
        orderDetails.setDeliveryStatus("Shipped");

        assertEquals("ORD124", orderDetails.getOrderId());
        assertEquals("Jane Doe", orderDetails.getCustomer());
        assertEquals("Laptop", orderDetails.getOrder());
        assertEquals(Date.valueOf("2024-07-02"), orderDetails.getDeliveryDate());
        assertEquals(999.99, orderDetails.getDeliveryPricing());
        assertEquals("Shipped", orderDetails.getDeliveryStatus());
    }

    @Test
    public void testBuilder() {
        OrderDetails orderDetails = OrderDetails.builder()
                .orderId("ORD125")
                .customer("Alice Smith")
                .order("Tablet")
                .deliveryDate(Date.valueOf("2024-07-03"))
                .deliveryPricing(499.99)
                .deliveryStatus("Delivered")
                .build();

        assertEquals("ORD125", orderDetails.getOrderId());
        assertEquals("Alice Smith", orderDetails.getCustomer());
        assertEquals("Tablet", orderDetails.getOrder());
        assertEquals(Date.valueOf("2024-07-03"), orderDetails.getDeliveryDate());
        assertEquals(499.99, orderDetails.getDeliveryPricing());
        assertEquals("Delivered", orderDetails.getDeliveryStatus());
    }
}
