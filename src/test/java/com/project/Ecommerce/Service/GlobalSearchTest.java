package com.project.Ecommerce.Service;

import com.project.Ecommerce.DataProvider;
import com.project.Ecommerce.model.OrderDetails;
import com.project.Ecommerce.model.OrderList;
import com.project.Ecommerce.model.PaginationDetails;
import com.project.Ecommerce.repo.OrderDetailsRepository;
import com.project.Ecommerce.service.GlobalSearch;
import com.project.Ecommerce.service.OrderDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GlobalSearchTest {

    @InjectMocks
    private GlobalSearch globalSearch;

    @Mock
    private OrderDetailsService orderDetailsService;

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    OrderList orderList = new OrderList();
    DataProvider data = new DataProvider();

    @Test
    public void testGetGlobalSearchResults() throws ParseException {
        // Arrange
        String surprise = "com";
        int pageNum = 1;
        OrderList orderList = data.getOrderList();
        PageRequest pageable = PageRequest.of(0, 11);
        Mockito.when(orderDetailsRepository.findByStatusCount(surprise)).thenReturn(1);
        Mockito.when(orderDetailsService.getPaginationDetails(pageNum, 1)).thenReturn(data.getOrderList().getPaginationDetails());
        Mockito.when(orderDetailsRepository.findByDeliveryStatus(surprise, pageable)).thenReturn(orderList.getOrderDetailsList());

        OrderList result = globalSearch.getGlobalSearchResults(surprise, pageNum);

        assertEquals(orderList.getOrderDetailsList().size(), result.getOrderDetailsList().size());
        assertEquals(orderList.getPaginationDetails().getTotalNumberOfPages(), result.getPaginationDetails().getTotalNumberOfPages());
    }



    @Test
    public void testGetGlobalSearchResultsWhenSurpriseStringIsNull() throws ParseException {
        // Arrange
        PaginationDetails paginationDetails= data.getOrderList().getPaginationDetails();
        String surprise = null;
        int pageNum = 1;
        OrderList orderList = data.getOrderList();
        int orderCount = (int) orderDetailsRepository.count();
        int cur = paginationDetails.getCurrentPage()-1;
        int offset = (paginationDetails.getCurrentPage() - 1) * 11;
        paginationDetails.setStartingRecord(offset+1);
        paginationDetails.setEndingRecord(Math.min((offset + 11), orderCount));
        Sort sort = Sort.by(Sort.Direction.ASC, "orderId");
        PageRequest pageable = PageRequest.of(cur, 11,sort);
        Page<OrderDetails> orderDetailsPage = new PageImpl<>(orderList.getOrderDetailsList());

        Mockito.when(orderDetailsRepository.count()).thenReturn(1L);
        Mockito.when(orderDetailsService.getPaginationDetails(pageNum,1)).thenReturn(paginationDetails);
        Mockito.when(orderDetailsRepository.findAll(pageable)).thenReturn(orderDetailsPage);

        OrderList result = globalSearch.getGlobalSearchResults(surprise, pageNum);


        assertEquals(orderList.getOrderDetailsList().size(), result.getOrderDetailsList().size());
        assertEquals(orderList.getPaginationDetails().getTotalNumberOfPages(), result.getPaginationDetails().getTotalNumberOfPages());
    }


}
