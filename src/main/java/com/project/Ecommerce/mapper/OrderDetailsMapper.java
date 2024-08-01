package com.project.Ecommerce.mapper;

import com.project.Ecommerce.model.OrderDetails;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderDetailsMapper implements RowMapper<OrderDetails> {

    @Override
    public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetails.OrderDetailsBuilder orderDetails = OrderDetails.builder();
        orderDetails.orderId(rs.getString("ORDER_ID"))
                .customer(rs.getString("CUSTOMER"))
                .order(rs.getString("ORDER_NAME"))
                .deliveryDate(incrementDateByOneDay(rs.getDate("DELIVERY_DATE")))
                .deliveryPricing(rs.getDouble("DELIVERY_PRICING"))
                .deliveryStatus(rs.getString("DELIVERY_STATUS"));
        return orderDetails.build();
    }

    private java.sql.Date incrementDateByOneDay(java.sql.Date date) {
        if (date != null) {
            LocalDate localDate = date.toLocalDate();
            LocalDate incrementedDate = localDate.plusDays(1);
            return java.sql.Date.valueOf(incrementedDate);
        }
        return null;
    }
}

