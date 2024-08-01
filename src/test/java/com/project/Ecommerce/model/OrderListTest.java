package com.project.Ecommerce.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListTest {

    @Test
    void testGettersAndSetters() {
        // Prepare test data
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(new OrderDetails("123", "John Doe", "Product X", null, 150.75, "Completed"));
        PaginationDetails paginationDetails = new PaginationDetails(1, 2, 1, 10);

        // Create OrderList object
        OrderList orderList = new OrderList();

        // Set values
        orderList.setOrderDetailsList(orderDetailsList);
        orderList.setPaginationDetails(paginationDetails);

        // Assert values
        assertEquals(orderDetailsList, orderList.getOrderDetailsList());
        assertEquals(paginationDetails, orderList.getPaginationDetails());
    }

    @Test
    void testAllArgsConstructor() {
        // Prepare test data
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(new OrderDetails("123", "John Doe", "Product X", null, 150.75, "Completed"));
        PaginationDetails paginationDetails = new PaginationDetails(1, 2, 1, 10);

        // Create OrderList object using all-args constructor
        OrderList orderList = new OrderList(orderDetailsList, paginationDetails);

        // Assert values
        assertEquals(orderDetailsList, orderList.getOrderDetailsList());
        assertEquals(paginationDetails, orderList.getPaginationDetails());
    }

    @Test
    void testNoArgsConstructor() {
        // Create OrderList object using no-args constructor
        OrderList orderList = new OrderList();

        // Assert default values
        assertNull(orderList.getOrderDetailsList());
        assertNull(orderList.getPaginationDetails());
    }

    @Test
    void testBuilder() {
        // Prepare test data
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(new OrderDetails("123", "John Doe", "Product X", null, 150.75, "Completed"));
        PaginationDetails paginationDetails = new PaginationDetails(1, 2, 1, 10);

        // Create OrderList object using builder
        OrderList orderList = OrderList.builder()
                .orderDetailsList(orderDetailsList)
                .paginationDetails(paginationDetails)
                .build();

        // Assert values
        assertEquals(orderDetailsList, orderList.getOrderDetailsList());
        assertEquals(paginationDetails, orderList.getPaginationDetails());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prepare test data
        List<OrderDetails> orderDetailsList1 = new ArrayList<>();
        orderDetailsList1.add(new OrderDetails("123", "John Doe", "Product X", null, 150.75, "Completed"));
        PaginationDetails paginationDetails1 = new PaginationDetails(1, 2, 1, 10);

        List<OrderDetails> orderDetailsList2 = new ArrayList<>();
        orderDetailsList2.add(new OrderDetails("123", "John Doe", "Product X", null, 150.75, "Completed"));
        PaginationDetails paginationDetails2 = new PaginationDetails(1, 2, 1, 10);

        // Create OrderList objects
        OrderList orderList1 = new OrderList(orderDetailsList1, paginationDetails1);
        OrderList orderList2 = new OrderList(orderDetailsList2, paginationDetails2);

        // Assert equality and hash code
        assertEquals(orderList1, orderList2);
        assertEquals(orderList1.hashCode(), orderList2.hashCode());
    }

    @Test
    void testToString() {
        // Prepare test data
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(new OrderDetails("123", "John Doe", "Product X", null, 150.75, "Completed"));
        PaginationDetails paginationDetails = new PaginationDetails(1, 2, 1, 10);

        // Create OrderList object
        OrderList orderList = new OrderList(orderDetailsList, paginationDetails);

        // Assert toString method
        String expectedToString = "OrderList(orderDetailsList=" + orderDetailsList.toString() + ", paginationDetails=" + paginationDetails.toString() + ")";
        assertEquals(expectedToString, orderList.toString());
    }
}
