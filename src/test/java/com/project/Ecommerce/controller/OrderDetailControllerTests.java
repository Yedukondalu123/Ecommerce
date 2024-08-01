package com.project.Ecommerce.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ecommerce.DataProvider;
import com.project.Ecommerce.exception.ArrayIndexException;
import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.service.GlobalSearch;
import com.project.Ecommerce.service.OrderDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.SimpleDateFormat;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderDetailController.class)
public class OrderDetailControllerTests {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    DataProvider data = new DataProvider();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderDetailsService orderDetailsService;

    @MockBean
    GlobalSearch globalSearch;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getOrderDetailsTest() throws Exception {

        int pageNum = 0;
        Filters filters = data.getFilterOrderIdStatusDeliveryDate();

        when(orderDetailsService.getOrderDetailsByFilter(pageNum, filters)).thenReturn(data.getOrderList());
        mockMvc.perform(post("/filters")
                        .param("page", String.valueOf(pageNum))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(filters)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testBadRequestWhenGivingInvalidParamName() throws Exception {
        Filters filters = new Filters();

        mockMvc.perform(post("/filters")
                        .param("pages", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(filters)))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    @Test
    public void testBadRequestWhenGivingInvalidDeliveryDate() throws Exception {
        Filters filters = new Filters();
        filters.setDeliveryDate("123//908");
        when(orderDetailsService.getOrderDetailsByFilter(0,filters)).thenThrow(new ArrayIndexException("Please check the deliveryDate value in filters. It should be in the format 'start - end'."));
        mockMvc.perform(post("/filters")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(filters)))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    @Test
    public void testBadRequestWhenGivingInvalidDeliveryPricing() throws Exception {
        Filters filters = new Filters();
        filters.setDeliveryPricing("123//908");
        when(orderDetailsService.getOrderDetailsByFilter(0,filters)).thenThrow(new ArrayIndexException("Please check deliveryPricing value in filters. It should be in the format of lowPrice-highPrice"));
        mockMvc.perform(post("/filters")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(filters)))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }


    @Test
    public void testGetOrderDetailsByHeaderFilter() throws Exception {
        int pageNum = 1;
        String headerFilter = "All Orders";
        mockMvc.perform(MockMvcRequestBuilders.get("/headers")
                        .param("page", String.valueOf(pageNum))
                        .param("headerFilter", headerFilter)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrderDetailsByGlobalSearch() throws Exception {
        int pageNum = 1;
        String filterName = "orderId";
        String filterValue = "ORD123";

        mockMvc.perform(MockMvcRequestBuilders.get("/globalSearch")
                        .param("page", String.valueOf(pageNum))
                        .param("filterName", filterName)
                        .param("filterValue", filterValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrderDetailsByGlobalSearchWhenFilterValueIsNull() throws Exception {
        int pageNum = 1;
        String filterName = "orderId";
        String filterValue = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/globalSearch")
                        .param("page", String.valueOf(pageNum))
                        .param("filterName", filterName)
                        .param("filterValue", filterValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrdersByGlobalSearch() throws Exception {
        int pageNum = 1;
        String surpriseString="com";

        mockMvc.perform(MockMvcRequestBuilders.get("/globalSearches")
                        .param("page", String.valueOf(pageNum))
                        .param("surpriseString", surpriseString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrdersByGlobalSearchWhenSurpriseStringIsNull() throws Exception {
        int pageNum = 1;
        String surpriseString=null;

        mockMvc.perform(MockMvcRequestBuilders.get("/globalSearches")
                        .param("page", String.valueOf(pageNum))
                        .param("surpriseString", surpriseString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
