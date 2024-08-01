package com.project.Ecommerce.mapper;

import com.project.Ecommerce.model.OrderDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderDetailsMapperTest {

    @Mock
    private ResultSet resultSet;

    private OrderDetailsMapper orderDetailsMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderDetailsMapper = new OrderDetailsMapper();
    }

    @Test
    void testMapRow() throws SQLException {
        // Mocking the ResultSet
        when(resultSet.getString("ORDER_ID")).thenReturn("123");
        when(resultSet.getString("CUSTOMER")).thenReturn("John Doe");
        when(resultSet.getString("ORDER_NAME")).thenReturn("Product X");
        when(resultSet.getDate("DELIVERY_DATE")).thenReturn(Date.valueOf("2023-07-28"));
        when(resultSet.getDouble("DELIVERY_PRICING")).thenReturn(199.99);
        when(resultSet.getString("DELIVERY_STATUS")).thenReturn("Completed");

        // Invoking the mapRow method
        OrderDetails orderDetails = orderDetailsMapper.mapRow(resultSet, 1);

        // Assertions
        assertNotNull(orderDetails);
        assertEquals("123", orderDetails.getOrderId());
        assertEquals("John Doe", orderDetails.getCustomer());
        assertEquals("Product X", orderDetails.getOrder());
        assertEquals(Date.valueOf("2023-07-29"), orderDetails.getDeliveryDate());
        assertEquals(199.99, orderDetails.getDeliveryPricing());
        assertEquals("Completed", orderDetails.getDeliveryStatus());

        // Verifying that the methods on the ResultSet were called
        verify(resultSet, times(1)).getString("ORDER_ID");
        verify(resultSet, times(1)).getString("CUSTOMER");
        verify(resultSet, times(1)).getString("ORDER_NAME");
        verify(resultSet, times(1)).getDate("DELIVERY_DATE");
        verify(resultSet, times(1)).getDouble("DELIVERY_PRICING");
        verify(resultSet, times(1)).getString("DELIVERY_STATUS");
    }
}
