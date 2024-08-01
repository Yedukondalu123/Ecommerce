package com.project.Ecommerce.repo;

import com.project.Ecommerce.model.OrderDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {

    //below are count and details based on orderId

    @Query("select o from OrderDetails o where upper(o.orderId) like upper(concat('%', :orderId, '%')) order by o.orderId")
    List<OrderDetails> findByOrderId(@Param("orderId") String orderId , Pageable pageable);

    @Query("select count(o) from OrderDetails o where upper(o.orderId) like upper(concat('%', :orderId, '%'))")
    int findByOrderIdCount(@Param("orderId") String orderId);


    // below are count and details on orderDetails

    @Query("select count(o) from OrderDetails o where lower(o.order) like lower(concat('%', :orderName, '%'))")
    int findByOrderCount(@Param("orderName") String orderName);

    @Query("select o from OrderDetails o where lower(o.order) like lower(concat('%', :orderName, '%')) order by o.orderId")
    List<OrderDetails> findByOrder(@Param("orderName") String orderName, Pageable pageable);

    // below are count and details based on deliveryStatus

    @Query("select count(o) from OrderDetails o where upper(o.deliveryStatus) like upper(concat('%', :deliveryStatus, '%'))")
    int findByStatusCount(@Param("deliveryStatus") String orderId);

    @Query("select o from OrderDetails o where lower(o.deliveryStatus) like lower(concat('%', :deliveryStatus, '%')) order by o.orderId")
    List<OrderDetails> findByDeliveryStatus(@Param("deliveryStatus") String deliveryStatus,Pageable pageable);


    // below are count and details based on customer

    @Query("select o from OrderDetails o where lower(o.customer) like lower(concat('%', :customer, '%')) order by o.orderId")
    List<OrderDetails> findByCustomer(@Param("customer") String customer,Pageable pageable);

    @Query("select count(o) from OrderDetails o where lower(o.customer) like lower(concat('%', :customer, '%'))")
    int findByCustomerCount(@Param("customer") String orderId);




}


