package com.project.Ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Filters {
    private String orderId;
    private String customer;
    private String orderItem;
    private String deliveryDate;
    private String deliveryPricing;
    private List<String> status;
}
