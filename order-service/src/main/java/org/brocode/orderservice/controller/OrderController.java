package org.brocode.orderservice.controller;


import lombok.RequiredArgsConstructor;
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
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return "Order placed successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }
}
