package com.project.Ecommerce.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaginationDetailsTest {

    @Test
    void testGettersAndSetters() {
        // Create PaginationDetails object
        PaginationDetails paginationDetails = new PaginationDetails();

        // Set values
        paginationDetails.setCurrentPage(1);
        paginationDetails.setTotalNumberOfPages(10);
        paginationDetails.setStartingRecord(1);
        paginationDetails.setEndingRecord(10);

        // Assert values
        assertEquals(1, paginationDetails.getCurrentPage());
        assertEquals(10, paginationDetails.getTotalNumberOfPages());
        assertEquals(1, paginationDetails.getStartingRecord());
        assertEquals(10, paginationDetails.getEndingRecord());
    }

    @Test
    void testAllArgsConstructor() {
        // Create PaginationDetails object using all-args constructor
        PaginationDetails paginationDetails = new PaginationDetails(1, 10, 1, 10);

        // Assert values
        assertEquals(1, paginationDetails.getCurrentPage());
        assertEquals(10, paginationDetails.getTotalNumberOfPages());
        assertEquals(1, paginationDetails.getStartingRecord());
        assertEquals(10, paginationDetails.getEndingRecord());
    }

    @Test
    void testNoArgsConstructor() {
        // Create PaginationDetails object using no-args constructor
        PaginationDetails paginationDetails = new PaginationDetails();

        // Assert default values
        assertNull(paginationDetails.getCurrentPage());
        assertNull(paginationDetails.getTotalNumberOfPages());
        assertNull(paginationDetails.getStartingRecord());
        assertNull(paginationDetails.getEndingRecord());
    }

    @Test
    void testEqualsAndHashCode() {
        // Create two identical PaginationDetails objects
        PaginationDetails paginationDetails1 = new PaginationDetails(1, 10, 1, 10);
        PaginationDetails paginationDetails2 = new PaginationDetails(1, 10, 1, 10);

        // Assert equality and hash code
        assertEquals(paginationDetails1, paginationDetails2);
        assertEquals(paginationDetails1.hashCode(), paginationDetails2.hashCode());
    }

    @Test
    void testToString() {
        // Create PaginationDetails object
        PaginationDetails paginationDetails = new PaginationDetails(1, 10, 1, 10);

        // Assert toString method
        String expectedToString = "PaginationDetails(currentPage=1, totalNumberOfPages=10, startingRecord=1, endingRecord=10)";
        assertEquals(expectedToString, paginationDetails.toString());
    }
}
