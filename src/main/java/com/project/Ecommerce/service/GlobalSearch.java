package com.project.Ecommerce.service;
import com.project.Ecommerce.model.OrderDetails;
import com.project.Ecommerce.model.OrderList;
import com.project.Ecommerce.model.PaginationDetails;
import com.project.Ecommerce.repo.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class GlobalSearch {

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    public OrderList getGlobalSearchResults(String surprise, int pageNum) {

        OrderList orderList = new OrderList();
        if (surprise != null && !surprise.isBlank()) {


            int orderCount= orderDetailsRepository.findByOrderIdCount(surprise);
            if (orderCount != 0) {


                PaginationDetails paginationDetails = orderDetailsService.getPaginationDetails(pageNum, orderCount);
                int offset = (paginationDetails.getCurrentPage() - 1) * 11;
                int cur = paginationDetails.getCurrentPage()-1;
                paginationDetails.setStartingRecord(offset+1);
                paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
                PageRequest pageable = PageRequest.of(cur, 11);

                List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(surprise,pageable);

                orderList.setOrderDetailsList(orderDetails);
                this.UpdateDates(orderList.getOrderDetailsList());
                orderList.setPaginationDetails(paginationDetails);
                return orderList;

            }else if (orderDetailsRepository.findByStatusCount(surprise)!=0) {
                orderCount = orderDetailsRepository.findByStatusCount(surprise);
                PaginationDetails paginationDetails = orderDetailsService.getPaginationDetails(pageNum, orderCount);

                int cur = paginationDetails.getCurrentPage()-1;
                int offset = (paginationDetails.getCurrentPage() - 1) * 11;
                paginationDetails.setStartingRecord(offset+1);
                paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
                PageRequest pageable = PageRequest.of(cur, 11);

                List<OrderDetails> orderDetailsList= orderDetailsRepository.findByDeliveryStatus(surprise,pageable);

                orderList.setOrderDetailsList(orderDetailsList);
                this.UpdateDates(orderList.getOrderDetailsList());
                orderList.setPaginationDetails(paginationDetails);
                return orderList;

            } else if (orderDetailsRepository.findByCustomerCount(surprise)!=0) {
                orderCount=orderDetailsRepository.findByCustomerCount(surprise);
                PaginationDetails paginationDetails = orderDetailsService.getPaginationDetails(pageNum, orderCount);

                int cur = paginationDetails.getCurrentPage()-1;
                int offset = (paginationDetails.getCurrentPage() - 1) * 11;
                paginationDetails.setStartingRecord(offset+1);
                paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
                PageRequest pageable = PageRequest.of(cur, 11);
                List<OrderDetails> orderDetailsList= orderDetailsRepository.findByCustomer(surprise,pageable);
                orderList.setOrderDetailsList(orderDetailsList);
                this.UpdateDates(orderList.getOrderDetailsList());
                orderList.setPaginationDetails(paginationDetails);
                return orderList;
            }
             else if (orderDetailsRepository.findByOrderCount(surprise)!=0) {

                orderCount=orderDetailsRepository.findByOrderCount(surprise);
                PaginationDetails paginationDetails = orderDetailsService.getPaginationDetails(pageNum, orderCount);

                int cur = paginationDetails.getCurrentPage()-1;
                int offset = (paginationDetails.getCurrentPage() - 1) * 11;
                paginationDetails.setStartingRecord(offset+1);
                paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
                PageRequest pageable = PageRequest.of(cur, 11);

                List<OrderDetails> orderDetailsList= orderDetailsRepository.findByOrder(surprise,pageable);

                orderList.setOrderDetailsList(orderDetailsList);
                this.UpdateDates(orderList.getOrderDetailsList());
                orderList.setPaginationDetails(paginationDetails);
                return orderList;
            }

            else {
                 orderList.setPaginationDetails(new PaginationDetails(1,1,1,1));
                 return  orderList;
            }

        }
        else {
            Sort sort = Sort.by(Sort.Direction.ASC, "orderId");
            int orderCount= (int) orderDetailsRepository.count();
            PaginationDetails paginationDetails = orderDetailsService.getPaginationDetails(pageNum, orderCount);
            int cur = paginationDetails.getCurrentPage()-1;
            int offset = (paginationDetails.getCurrentPage() - 1) * 11;
            paginationDetails.setStartingRecord(offset+1);
            paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
            PageRequest pageable = PageRequest.of(cur, 11,sort);
            Page<OrderDetails> orderDetailsPage = orderDetailsRepository.findAll(pageable);
            List<OrderDetails> orderDetailsList = orderDetailsPage.getContent();


            orderList.setOrderDetailsList(orderDetailsList);
            this.UpdateDates(orderList.getOrderDetailsList());
            orderList.setPaginationDetails(paginationDetails);
            return orderList;


        }
    }


    private void UpdateDates(List<OrderDetails> orderDetailsList)
    {
        orderDetailsList.forEach(order -> {
            LocalDate localDate = order.getDeliveryDate().toLocalDate();
            LocalDate incrementedDate = localDate.plusDays(1);
            order.setDeliveryDate(Date.valueOf(incrementedDate));
        });
    }

    public List<OrderDetails> getAllRecord()
    {
        return orderDetailsRepository.findAll();
    }


}
