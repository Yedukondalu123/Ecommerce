package com.project.Ecommerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="orders")
public class OrderDetails {
    @Id
    @Column(name="ORDER_ID")
    private String orderId;

    @Column(name="CUSTOMER")
    private String customer;

    @Column(name="ORDER_NAME")
    private String order;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="DELIVERY_DATE")
    private Date deliveryDate;

    @Column(name="DELIVERY_PRICING")
    private double deliveryPricing;

    @Column(name="DELIVERY_STATUS")
    private String deliveryStatus;

}

