package org.brocode.orderservice.controller;


import lombok.RequiredArgsConstructor;
import org.brocode.orderservice.Exception.NotFoundException;
import org.brocode.orderservice.Exception.ProductOutOfStockException;
import org.brocode.orderservice.dto.ExceptionDto;
import org.brocode.orderservice.dto.OrderLineItemsDto;
import org.brocode.orderservice.dto.OrderRequest;
import org.brocode.orderservice.dto.OrderResponseDTO;
import org.brocode.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) throws ProductOutOfStockException {
        if(orderService.createOrder(orderRequest))
            return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
        return new ResponseEntity<>("Unable to place order due to insufficient items", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public String createOrder(@RequestBody OrderRequest orderRequest){
//
//        orderService.createOrder(orderRequest);
//        return("Order placed Succesfully");
//    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ExceptionDto> handleOutOfStockException(ProductOutOfStockException productOutOfStockException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR,productOutOfStockException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND,notFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable ("id") int id) throws NotFoundException {
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }
}
