package com.project.Ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderList {
    private List<OrderDetails> orderDetailsList;
    private PaginationDetails paginationDetails;
}
