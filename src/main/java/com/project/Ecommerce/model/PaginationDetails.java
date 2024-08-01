package com.project.Ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginationDetails {
    private Integer currentPage;
    private Integer totalNumberOfPages;
    private Integer startingRecord;
    private Integer endingRecord;
}
