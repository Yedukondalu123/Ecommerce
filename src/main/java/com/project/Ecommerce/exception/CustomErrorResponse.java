package com.project.Ecommerce.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomErrorResponse {

    private HttpStatus status;
    private String message;
}
