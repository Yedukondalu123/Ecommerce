package com.project.Ecommerce.repo;

import com.project.Ecommerce.mapper.OrderDetailsMapper;
import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.model.OrderDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderDetailsRepoTest {

    @InjectMocks
    private OrderDetailsRepo orderDetailsRepo;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderCountByFilter() {
        Filters filters = new Filters();
        filters.setOrderId("123");

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(10);

        int count = orderDetailsRepo.getOrderCountByFilter(filters);

        assertEquals(10, count);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class));
    }

    @Test
    void testGetOrderCountForHeader_validHeaderFilter() {
        String headerFilter = "Completed";

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(20);

        int count = orderDetailsRepo.getOrderCountForHeader(headerFilter);

        assertEquals(20, count);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class));
    }

    @Test
    void testGetOrderCountForHeader_invalidHeaderFilter() {
        String headerFilter = "InvalidFilter";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderDetailsRepo.getOrderCountForHeader(headerFilter));

        assertEquals("Invalid headerFilter value. Valid values are: [All Orders, Completed, Canceled, Restitute, Continuing]", exception.getMessage());
    }

    @Test
    void testGetOrderCountForGlobalSearch_validFilterName() {
        String filterName = "customer";
        String filterValue = "John Doe";

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class)))
                .thenReturn(30);

        int count = orderDetailsRepo.getOrderCountForGlobalSearch(filterName, filterValue);

        assertEquals(30, count);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), eq(Integer.class));
    }

    @Test
    void testGetOrderCountForGlobalSearch_invalidFilterName() {
        String filterName = "invalidFilter";
        String filterValue = "value";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderDetailsRepo.getOrderCountForGlobalSearch(filterName, filterValue));

        assertEquals("Invalid filter name:invalidFilter", exception.getMessage());
    }

    @Test
    void testGetOrderDetailsByFilter_withFilters() {
        Filters filters = new Filters();
        filters.setCustomer("John Doe");
        PageRequest pageable = PageRequest.of(0, 10);

        List<OrderDetails> mockOrders = new ArrayList<>();
        mockOrders.add(new OrderDetails());

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(OrderDetailsMapper.class)))
                .thenReturn(mockOrders);

        List<OrderDetails> orders = orderDetailsRepo.getOrderDetailsByFilter(pageable, filters);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(namedParameterJdbcTemplate, times(1)).query(anyString(), any(MapSqlParameterSource.class), any(OrderDetailsMapper.class));
    }

    @Test
    void testGetOrderDetailsByGlobalFilter() {
        PageRequest pageable = PageRequest.of(0, 10);
        String filterName = "customer";
        String filterValue = "John Doe";

        List<OrderDetails> mockOrders = new ArrayList<>();
        mockOrders.add(new OrderDetails());

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(OrderDetailsMapper.class)))
                .thenReturn(mockOrders);

        List<OrderDetails> orders = orderDetailsRepo.getOrderDetailsByGlobalFilter(pageable, filterName, filterValue);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(namedParameterJdbcTemplate, times(1)).query(anyString(), any(MapSqlParameterSource.class), any(OrderDetailsMapper.class));
    }

    @Test
    void testGetOrderDetailsByHeaderFilter() {
        PageRequest pageable = PageRequest.of(0, 10);
        String headerFilter = "Completed";

        List<OrderDetails> mockOrders = new ArrayList<>();
        mockOrders.add(new OrderDetails());

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(OrderDetailsMapper.class)))
                .thenReturn(mockOrders);

        List<OrderDetails> orders = orderDetailsRepo.getOrderDetailsByHeaderFilter(pageable, headerFilter);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(namedParameterJdbcTemplate, times(1)).query(anyString(), any(MapSqlParameterSource.class), any(OrderDetailsMapper.class));
    }
}
