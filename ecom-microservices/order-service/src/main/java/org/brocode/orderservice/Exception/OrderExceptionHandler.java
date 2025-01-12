package org.brocode.orderservice.Exception;

import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<OrderServiceErrorResponse> handleProductOutOfStockException(ProductOutOfStockException ex){

        OrderServiceErrorResponse errorResponse = new OrderServiceErrorResponse("PRODUCT_OUT_OF_STOCK", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<OrderServiceErrorResponse> dateTimeParseException(DateTimeParseException de){
        OrderServiceErrorResponse errorResponse = new OrderServiceErrorResponse(
                "Date_Parse_Error",
                de.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<OrderServiceErrorResponse> handleNotFoundException(NotFoundException ex) {
        OrderServiceErrorResponse errorResponse = new OrderServiceErrorResponse(
                "NOT_FOUND",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
