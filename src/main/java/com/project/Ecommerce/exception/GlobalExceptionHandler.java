package com.project.Ecommerce.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArrayIndexException.class)
    public ResponseEntity<String> handleArrayIndexOutOfBoundsException(ArrayIndexException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }



    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<String> handleUnrecognizedPropertyException(UnrecognizedPropertyException e) {
        // Get the unrecognized field name from the exception
        String fieldName = e.getPropertyName();

        // Create a custom error message
        String errorMessage = "Invalid JSON format: JSON parse error: Unrecognized field '" + fieldName + "'.";

        // Return a 400 Bad Request status and the custom error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }




        @ExceptionHandler(UnsupportedEncodingException.class)
        public ResponseEntity<CustomErrorResponse> handleUnsupportedEncodingException(UnsupportedEncodingException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse();
            errorResponse.setStatus(HttpStatus.BAD_REQUEST);
            errorResponse.setMessage("Invalid search, Please check your input.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }



}

