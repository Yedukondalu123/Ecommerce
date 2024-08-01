package com.project.Ecommerce.repo;

import com.project.Ecommerce.exception.ArrayIndexException;
import com.project.Ecommerce.mapper.OrderDetailsMapper;
import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Repository
public class OrderDetailsRepo {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int getOrderCountByFilter(Filters filters)
    {
        StringBuilder query = new StringBuilder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        int count=0;
        if(!ObjectUtils.isEmpty(filters))
        {
            query.append("select count(*) from Orders");
            this.updateSqlByFilter(filters, query, parameterSource);
        }
        else {
            query.append("select count(*) from Orders");
        }

        try
        {
            Integer result = namedParameterJdbcTemplate.queryForObject(query.toString(),parameterSource,Integer.class);
            count=(result!=null)?result:0;
        }
        catch(EmptyResultDataAccessException e)
        {
            count = 0;
        }

        return count;
    }

    List<String> filters = Arrays.asList("All Orders","Completed","Canceled","Restitute","Continuing");
    private boolean checkHeaderFilter(String headerFilter)
    {
        if(headerFilter.charAt(0)>='A' && headerFilter.charAt(0)<='Z')
        {
            return filters.contains(headerFilter);
        }
        else
            return false;
    }

    public int getOrderCountForHeader(String headerFilter)
    {
        StringBuilder query = new StringBuilder();
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        int count=0;
        if(headerFilter!=null && !headerFilter.isBlank())
        {
            if(checkHeaderFilter(headerFilter)) {
                if (headerFilter.equals("All Orders")) {
                    query.append("select count(*) from orders");
                } else {
                    query.append("select count(*) from Orders where DELIVERY_STATUS = :deliveryStatus");
                    parameterSource.addValue("deliveryStatus", headerFilter);
                }
            }
            else
                throw new IllegalArgumentException("Invalid headerFilter value. Valid values are: " + filters);

        }
        else
            query.append("select count(*) from orders");

        try
        {
            Integer result = namedParameterJdbcTemplate.queryForObject(query.toString(),parameterSource,Integer.class);
            count=(result!=null)?result:0;
        }
        catch(TransientDataAccessException e)
        {
            count = 0;
        }
        return count;

    }
    private boolean checkFilterName(String filterName)
    {
        List<String> filters = Arrays.asList("orderId","customer","orderName","deliveryDate","deliveryPricing","deliveryStatus");
        return filters.contains(filterName);
    }

    public int getOrderCountForGlobalSearch(String filterName,String filterValue)
    {
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        if(filterName!=null && !filterName.isBlank())
        {
            if(checkFilterName(filterName)) {
                if (filterValue != null && !filterValue.isBlank()) {
                    query.append("select count(*) from orders");
                    this.updateQueryByGlobalSearch(query, filterName, filterValue, parameterSource);
                }
                else
                    query.append("select count(*) from orders");

            }
            else
                throw new IllegalArgumentException("Invalid filter name:" + filterName);
        }
        else
            throw new IllegalArgumentException("filterName is Null or Blank");

        int count=0;
        try
        {
            Integer result = namedParameterJdbcTemplate.queryForObject(query.toString(),parameterSource,Integer.class);
            count=(result!=null)?result:0;
        }
        catch(EmptyResultDataAccessException e)
        {
            count = 0;
        }
        catch(UncategorizedDataAccessException e)
        {
            count =0;
        }
        return count;
    }

    private void updateSqlByFilter(Filters filters,StringBuilder query, MapSqlParameterSource parameterSource)
    {
        if(filters.getOrderId()!=null && !filters.getOrderId().isBlank())
        {
            if(this.queryCondition(query))
            {
                query.append(" where upper(ORDER_ID) = upper(:orderID)");
            }
            else
                query.append(" and upper(ORDER_ID)  = upper(:orderID)");

            parameterSource.addValue("orderID",filters.getOrderId());
        }
        if(filters.getCustomer()!=null && !filters.getCustomer().isBlank())
        {
            if(this.queryCondition(query))
            {
                query.append(" where upper(CUSTOMER) = upper(:customer)");
            }
            else
                query.append(" and upper(CUSTOMER) = upper(:customer)");
            parameterSource.addValue("customer",filters.getCustomer());
        }
        if(filters.getOrderItem()!=null && !filters.getOrderItem().isBlank())
        {
            if(this.queryCondition(query))
            {
                query.append(" where ORDER_NAME =:orderName");
            }
            else
                query.append(" and ORDER_NAME = :orderName ");
            parameterSource.addValue("orderName",filters.getOrderItem());
        }
        try {
            if (filters.getDeliveryDate() != null && !filters.getDeliveryDate().isBlank()) {
                List<String> s1 = Arrays.asList(filters.getDeliveryDate().split(" - "));
                if (this.queryCondition(query)) {
                    query.append(" where DELIVERY_DATE BETWEEN ");
                    query.append(":startingDate");
                    query.append(" AND ");
                    query.append(":endingDate");
                } else
                    query.append(" and DELIVERY_DATE BETWEEN :startingDate AND :endingDate");
                parameterSource.addValue("startingDate", LocalDate.parse(s1.get(0))).addValue("endingDate", LocalDate.parse(s1.get(1)));
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
            {
                throw new ArrayIndexException("Please check the deliveryDate value in filters. It should be in the format 'start - end'.");
            }

        try {
            if (filters.getDeliveryPricing() != null && !filters.getDeliveryPricing().isBlank()) {
                List<String> s1 = Arrays.asList(filters.getDeliveryPricing().split("-"));
                if (this.queryCondition(query)) {
                    query.append(" where DELIVERY_PRICING BETWEEN :lowPrice AND :highPrice");
                } else
                    query.append(" and DELIVERY_PRICING BETWEEN :lowPrice AND :highPrice");
                parameterSource.addValue("lowPrice", s1.get(0)).addValue("highPrice", s1.get(1));
            }
        } catch(ArrayIndexOutOfBoundsException ex)
        {
            throw new ArrayIndexException("Please check deliveryPricing value in filters. It should be in the format of lowPrice-highPrice");
        }

            if (filters.getStatus() != null && !filters.getStatus().isEmpty()) {
                if (this.queryCondition(query)) {
                    query.append(" where DELIVERY_STATUS IN (:status)");
                } else
                    query.append(" and DELIVERY_STATUS IN (:status)");
                parameterSource.addValue("status", filters.getStatus());
            }

    }
    private boolean queryCondition(StringBuilder query)
    {
        return query.toString().equals("select count(*) from Orders") || query.toString().equals("SELECT * FROM Orders");
    }

    public List<OrderDetails> getOrderDetailsByFilter(PageRequest pageable, Filters filters) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("OFFSET", pageable.getPageNumber()).addValue("LIMIT", 11);
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM Orders");


        try {
            if(!ObjectUtils.isEmpty(filters)) {
               this.updateSqlByFilter(filters,query,parameterSource);
               query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
            }else
                query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
            return namedParameterJdbcTemplate.query(query.toString(),parameterSource,new OrderDetailsMapper());
        }
        catch(IllegalArgumentException e)
        {
          throw new IllegalArgumentException("FilterName is Null or Blank");
        }
    }

    public List<OrderDetails> getOrderDetailsByGlobalFilter(PageRequest pageable,String filterName,String filterValue)
    {
        StringBuilder query = new StringBuilder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("OFFSET", pageable.getPageNumber()).addValue("LIMIT", 11);
        try
        {
            if(filterName!=null && !filterName.isBlank())
            {
                if(filterValue!=null && !filterValue.isBlank()) {
                    query.append("select * from orders");
                    this.updateQueryByGlobalSearch(query, filterName, filterValue, parameterSource);
                    query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
                }
                else {
                    query.append("select * from orders");
                    query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
                }
            }
            return namedParameterJdbcTemplate.query(query.toString(),parameterSource,new OrderDetailsMapper());
        }
        catch(IllegalArgumentException e)
        {
            throw new IllegalArgumentException("FilterName is Null or Blank");
        }
    }

    public List<OrderDetails> getOrderDetailsByHeaderFilter(PageRequest pageable, String headerFilter)
    {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("OFFSET", pageable.getPageNumber()).addValue("LIMIT", 11);
        StringBuilder query = new StringBuilder();
        if(headerFilter!=null && !headerFilter.isBlank()) {

            if (headerFilter.equals("All Orders")) {
                query.append("SELECT * FROM orders");
                query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
            } else {
                query.append("SELECT * FROM orders");
                query.append(" where DELIVERY_STATUS = :headerFilter");
                query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
                parameterSource.addValue("headerFilter", headerFilter);

            }
        }
        else
        {
            query.append("SELECT * FROM orders");
            query.append(" ORDER BY ORDER_ID LIMIT :OFFSET,:LIMIT");
        }

        return namedParameterJdbcTemplate.query(query.toString(),parameterSource,new OrderDetailsMapper());
    }

    private void updateQueryByGlobalSearch(StringBuilder query, String filterName, String filterValue, MapSqlParameterSource parameterSource)
    {
        if(filterName.equals("orderId")){
            query.append(" where ORDER_ID = :orderId");
            parameterSource.addValue("orderId",filterValue);
        }
        else if(filterName.equals("customer"))
        {
            query.append(" where CUSTOMER =:customerName");
            parameterSource.addValue("customerName",filterValue);
        }
        else if(filterName.equals("orderName"))
        {
            query.append(" where ORDER_NAME = :orderName");
            parameterSource.addValue("orderName",filterValue);
        }
        else if(filterName.equals("deliveryDate"))
        {
            query.append(" where DATE(DELIVERY_DATE) =:deliveryDate");
            parameterSource.addValue("deliveryDate",filterValue);
        }
        else if(filterName.equals("deliveryPricing"))
        {
            query.append(" where DELIVERY_PRICING = :deliveryPricing");
            parameterSource.addValue("deliveryPricing",filterValue);
        }
        else if(filterName.equals("deliveryStatus"))
        {
            query.append(" where DELIVERY_STATUS = :deliveryStatus");
            parameterSource.addValue("deliveryStatus",filterValue);
        }
    }

}



