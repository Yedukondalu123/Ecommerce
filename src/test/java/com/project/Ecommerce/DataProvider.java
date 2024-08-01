package com.project.Ecommerce;

import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.model.OrderDetails;
import com.project.Ecommerce.model.OrderList;
import com.project.Ecommerce.model.PaginationDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class DataProvider {

    OrderList orderList ;

    public OrderList getOrderList() throws ParseException {

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        OrderDetails orderDetails = new OrderDetails();
        orderList = new OrderList();

        orderDetails.setOrderId("ORD132");
        orderDetails.setCustomer("Irene Adams");
        orderDetails.setOrder("iPhone 13");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse("2024-07-20");
        LocalDate localDate = utilDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        orderDetails.setDeliveryDate(Date.valueOf(localDate));
        orderDetails.setDeliveryPricing(699.0);
        orderDetails.setDeliveryStatus("Completed");

        orderDetailsList.add(orderDetails);

        orderList.setOrderDetailsList(orderDetailsList);

        PaginationDetails paginationDetails = new PaginationDetails();
        paginationDetails.setCurrentPage(1);
        paginationDetails.setTotalNumberOfPages(1);
        paginationDetails.setStartingRecord(1);
        paginationDetails.setEndingRecord(1);

        orderList.setPaginationDetails(paginationDetails);

        return orderList;
    }

    public Filters getFilterOrderIdStatusDeliveryDate(){
        Filters filters = new Filters();
        filters.setOrderId("ORD132");
        filters.setStatus(List.of("Completed","Cancelled"));
        filters.setDeliveryDate("2024-09-21 - 2024-12-34");

        return filters;
    }
}
