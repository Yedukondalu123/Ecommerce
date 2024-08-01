package com.project.Ecommerce.service;

import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.model.OrderList;
import com.project.Ecommerce.model.PaginationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.project.Ecommerce.repo.OrderDetailsRepo;

import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    public PaginationDetails getPaginationDetails(int pageNum, int orderCount) {
        PaginationDetails paginationDetails = new PaginationDetails();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if(orderCount >= 0) {
            paginationDetails.setCurrentPage(pageNum);
            paginationDetails.setTotalNumberOfPages((orderCount % 11 == 0 ? orderCount / 11 : orderCount / 11 + 1));
            if(pageNum > paginationDetails.getTotalNumberOfPages()) {
                paginationDetails.setCurrentPage(1);
            }
        }
        return paginationDetails;
    }

    public OrderList getOrderDetailsByFilter(int pageNum, Filters filters) {
        int orderCount = orderDetailsRepo.getOrderCountByFilter(filters);
        OrderList orderList = new OrderList();
        PaginationDetails paginationDetails = getPaginationDetails(pageNum, orderCount);
        orderList.setPaginationDetails(paginationDetails);
        if(orderCount > 0) {
            int offset = (paginationDetails.getCurrentPage() - 1) * 11;
            paginationDetails.setStartingRecord(offset+1);
            paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
            PageRequest pageable = PageRequest.of(offset, 11);
            orderList.setOrderDetailsList(orderDetailsRepo.getOrderDetailsByFilter(pageable, filters));
        }
        return orderList;
    }

    public OrderList getOrderDetailsByHeader(int pageNum, String headerFilter) {
        int orderCount = orderDetailsRepo.getOrderCountForHeader(headerFilter);
        OrderList orderList = new OrderList();
        PaginationDetails paginationDetails = getPaginationDetails(pageNum, orderCount);
        orderList.setPaginationDetails(paginationDetails);
        if(orderCount > 0) {
            int offset = (paginationDetails.getCurrentPage() - 1) * 11;
            paginationDetails.setStartingRecord(offset+1);
            paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
            PageRequest pageable = PageRequest.of(offset, 11);
            orderList.setOrderDetailsList(orderDetailsRepo.getOrderDetailsByHeaderFilter(pageable, headerFilter));
        }
        return orderList;
    }

    public OrderList getOrderDetailsByGlobalSearch(int pageNum, String filterName, String filterValue) {
        int orderCount = orderDetailsRepo.getOrderCountForGlobalSearch(filterName, filterValue);
        OrderList orderList = new OrderList();
        PaginationDetails paginationDetails = getPaginationDetails(pageNum, orderCount);
        orderList.setPaginationDetails(paginationDetails);
        if(orderCount > 0) {
            int offset = (paginationDetails.getCurrentPage() - 1) * 11;
            paginationDetails.setStartingRecord(offset+1);
            paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
            PageRequest pageable = PageRequest.of(offset, 11);
            orderList.setOrderDetailsList(orderDetailsRepo.getOrderDetailsByGlobalFilter(pageable, filterName, filterValue));
        }
        return orderList;
    }

}



