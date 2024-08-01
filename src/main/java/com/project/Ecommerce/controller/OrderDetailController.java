package com.project.Ecommerce.controller;

import com.project.Ecommerce.model.Filters;
import com.project.Ecommerce.model.OrderDetails;
import com.project.Ecommerce.model.OrderList;
import com.project.Ecommerce.service.GlobalSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Ecommerce.service.OrderDetailsService;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private GlobalSearch globalSearch;

    @PostMapping("/filters")
    public ResponseEntity<OrderList> getOrderDetails(@RequestParam(name="page") int pageNum,
                                                     @RequestBody(required = false) Filters filters)  {
        return ResponseEntity.status(HttpStatus.OK).body(orderDetailsService.getOrderDetailsByFilter(pageNum, filters));
    }



    @GetMapping("/headers")
    public ResponseEntity<OrderList> getOrderDetailsByHeaderFilter(@RequestParam(name="page") int pageNum,
                                                                   @RequestParam(name ="headerFilter",required = false) String headerFilter){

        return ResponseEntity.status(HttpStatus.OK).body(orderDetailsService.getOrderDetailsByHeader(pageNum,headerFilter));
    }


    @GetMapping("/globalSearch")
    public ResponseEntity<OrderList> getOrderDetailsByGlobalSearch(@RequestParam(name="page") int pageNum,
                                                                   @RequestParam(required = false) String filterName,
                                                                   @RequestParam(required = false) String filterValue){
            return ResponseEntity.status(HttpStatus.OK).body(orderDetailsService.getOrderDetailsByGlobalSearch(pageNum,filterName,filterValue));


    }


    @GetMapping("/globalSearches")
    public ResponseEntity<OrderList> getOrdersByGlobalSearch(@RequestParam(name="surpriseText",required = false) String surpriseText,
                                                             @RequestParam(name="page") int pageNum)
    {

                OrderList orderList= globalSearch.getGlobalSearchResults(surpriseText,pageNum);
                return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }





    @GetMapping("/getAllRecords")
    public ResponseEntity<List<OrderDetails>> getAllRecords()
    {
        return ResponseEntity.status(HttpStatus.OK).body(globalSearch.getAllRecord());
    }



}









