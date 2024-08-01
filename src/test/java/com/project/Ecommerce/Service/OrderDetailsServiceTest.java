package com.project.Ecommerce.Service;

import com.project.Ecommerce.DataProvider;
import com.project.Ecommerce.exception.ArrayIndexException;
import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.model.OrderList;
import com.project.Ecommerce.repo.OrderDetailsRepo;
import com.project.Ecommerce.service.OrderDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import java.text.ParseException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderDetailsServiceTest {

    DataProvider data = new DataProvider();


    @InjectMocks
    private OrderDetailsService orderDetailsService;

    @Mock
    private OrderDetailsRepo orderDetailsRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrderDetailsByFilter() throws ParseException {
        int pageNum = 1;


        OrderList orderList = data.getOrderList();
        Filters filters = data.getFilterOrderIdStatusDeliveryDate();

        int offset = (pageNum - 1) * 11;
        PageRequest pageable = PageRequest.of(offset,11);
        pageable=PageRequest.of(0,11);
        when(orderDetailsRepo.getOrderCountByFilter(filters)).thenReturn(1);
        when(orderDetailsRepo.getOrderDetailsByFilter(pageable,filters)).thenReturn(orderList.getOrderDetailsList());

        OrderList result = orderDetailsService.getOrderDetailsByFilter(1, filters);
        Assertions.assertEquals(result,orderList);
    }

    @Test
    public void testGetOrderDetailsByFilterWhenFiltersAreNull() throws ParseException {
        int pageNum = 1;

        OrderList orderList = data.getOrderList();
        Filters filters = null;

        int offset = (pageNum - 1) * 11;
        PageRequest pageable = PageRequest.of(offset,11);
        pageable=PageRequest.of(0,11);
        when(orderDetailsRepo.getOrderCountByFilter(filters)).thenReturn(1);
        when(orderDetailsRepo.getOrderDetailsByFilter(pageable,filters)).thenReturn(orderList.getOrderDetailsList());

        OrderList result = orderDetailsService.getOrderDetailsByFilter(1, filters);
        Assertions.assertEquals(result,orderList);
    }

    @Test
    public void testGetOrderDetailsByFilterWhenDeliveryDateIsInvalid() throws ParseException {
        int pageNum = 1;
        Filters filters = new Filters();
        filters.setDeliveryDate("2024-04-23-2024-06-20");

        int offset = (pageNum - 1) * 11;
        PageRequest pageable = PageRequest.of(offset,11);
        pageable=PageRequest.of(0,11);
        when(orderDetailsRepo.getOrderCountByFilter(filters)).thenThrow(new ArrayIndexException("Please check the deliveryDate value in filters. It should be in the format 'start - end'."));

        // Assert that an exception is thrown
        Assertions.assertThrows(ArrayIndexException.class, () -> {
            // This should throw an exception
            OrderList result = orderDetailsService.getOrderDetailsByFilter(1, filters);
        });
    }

    @Test
    public void testGetOrderDetailsByFilterWhenDeliveryPricingIsInvalid() throws ParseException {
        int pageNum = 1;
        Filters filters = new Filters();
        filters.setDeliveryPricing("12//90");

        int offset = (pageNum - 1) * 11;
        PageRequest pageable = PageRequest.of(offset,11);
        pageable=PageRequest.of(0,11);
        when(orderDetailsRepo.getOrderCountByFilter(filters)).thenThrow(new ArrayIndexException("Please check the deliveryDate value in filters. It should be in the format 'start - end'."));

        // Assert that an exception is thrown
        Assertions.assertThrows(ArrayIndexException.class, () -> {
            // This should throw an exception
            OrderList result = orderDetailsService.getOrderDetailsByFilter(1, filters);
        });
    }





    @Test
    public void testGetOrderDetailsByHeader() {
        int pageNum=2;
        int offset = (pageNum - 1) * 11;
        PageRequest pageable = PageRequest.of(offset,11);
        String headerFilter = "All Orders";

        when(orderDetailsRepo.getOrderCountForHeader(headerFilter)).thenReturn(15);
        when(orderDetailsRepo.getOrderDetailsByHeaderFilter(pageable,headerFilter)).thenReturn(new OrderList().getOrderDetailsList());
        OrderList result = orderDetailsService.getOrderDetailsByHeader(2, headerFilter);

        assertNotNull(result);
        verify(orderDetailsRepo, times(1)).getOrderDetailsByHeaderFilter(pageable,"All Orders");
    }

    @Test
    public void testGetOrderDetailsByHeaderWhenHeaderFilterIsNull() {
        int pageNum=2;
        int offset = (pageNum - 1) * 11;
        PageRequest pageable = PageRequest.of(offset,11);
        String headerFilter = null;

        when(orderDetailsRepo.getOrderCountForHeader(headerFilter)).thenReturn(39);
        when(orderDetailsRepo.getOrderDetailsByHeaderFilter(pageable,headerFilter)).thenReturn(new OrderList().getOrderDetailsList());
        OrderList result = orderDetailsService.getOrderDetailsByHeader(2, headerFilter);

        assertNotNull(result);
        verify(orderDetailsRepo, times(1)).getOrderDetailsByHeaderFilter(pageable,headerFilter);
    }

    @Test
    public void testGetOrderDetailsByGlobalSearch() {
        String filterName = "name";
        String filterValue = "value";
        when(orderDetailsRepo.getOrderCountForGlobalSearch(filterName, filterValue)).thenReturn(1);
        OrderList result = orderDetailsService.getOrderDetailsByGlobalSearch(1, filterName, filterValue);

        assertNotNull(result);

        verify(orderDetailsRepo, times(1)).getOrderDetailsByGlobalFilter(any(PageRequest.class), eq(filterName), eq(filterValue));
    }


    @Test
    public void testGetOrderDetailsByGlobalSearchWhenFilterValueIsNull() {
        String filterName = "name";
        String filterValue = null;
        when(orderDetailsRepo.getOrderCountForGlobalSearch(filterName, filterValue)).thenReturn(1);
        OrderList result = orderDetailsService.getOrderDetailsByGlobalSearch(1, filterName, filterValue);

        assertNotNull(result);

        verify(orderDetailsRepo, times(1)).getOrderDetailsByGlobalFilter(any(PageRequest.class), eq(filterName), eq(filterValue));
    }





}