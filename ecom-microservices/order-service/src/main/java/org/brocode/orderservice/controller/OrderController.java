package org.brocode.orderservice.controller;


import lombok.RequiredArgsConstructor;
import org.brocode.orderservice.Exception.NotFoundException;
import org.brocode.orderservice.Exception.ProductOutOfStockException;
import org.brocode.orderservice.dto.ExceptionDto;
import org.brocode.orderservice.dto.OrderLineItemsDto;
import org.brocode.orderservice.dto.OrderRequest;
import org.brocode.orderservice.dto.OrderResponseDTO;
import org.brocode.orderservice.model.Order;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable ("id") int id) throws NotFoundException {
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<OrderResponseDTO>> getOrderByDate(@PathVariable ("date") String date) throws NotFoundException{
        return new ResponseEntity<>(orderService.getOrderByDate(date), HttpStatus.OK);
    }


//    @GetMapping("/orders")
//    public ResponseEntity<?> getOrders(
//            @RequestParam(required = false) Integer id,
//            @RequestParam(required = false) String date) throws NotFoundException {
//        if (id != null) {
//            OrderResponseDTO order = orderService.getOrderById(id);
//            return ResponseEntity.ok(order);
//        } else if (date != null) {
//            List<OrderResponseDTO> orders = orderService.getOrderByDate(date);
//            return ResponseEntity.ok(orders);
//        }
//        return ResponseEntity.badRequest().body("Please provide either an ID or a date.");
//    }

}
