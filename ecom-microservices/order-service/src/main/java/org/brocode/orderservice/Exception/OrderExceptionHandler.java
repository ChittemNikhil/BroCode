package org.brocode.orderservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<OrderServiceErrorResponse> handleProductOutOfStockException(ProductOutOfStockException ex){

        OrderServiceErrorResponse errorResponse = new OrderServiceErrorResponse("PRODUCT_OUT_OF_STOCK", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
