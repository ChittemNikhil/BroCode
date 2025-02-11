package org.brocode.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brocode.orderservice.Exception.NotFoundException;
import org.brocode.orderservice.Exception.ProductOutOfStockException;
import org.brocode.orderservice.dto.InventoryResponse;
import org.brocode.orderservice.dto.OrderLineItemsDto;
import org.brocode.orderservice.dto.OrderRequest;
import org.brocode.orderservice.dto.OrderResponseDTO;
import org.brocode.orderservice.model.Order;
import org.brocode.orderservice.model.OrderLineItems;
import org.brocode.orderservice.repository.OrderRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;


    public boolean createOrder(OrderRequest orderRequest) throws ProductOutOfStockException {

        Order order = mapOrderRequestToOrder(orderRequest);
        log.info("order request details service: {}" , orderRequest.getOrderLineItemsDtoList());

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        if (!inventoryService.isStockAvailable(skuCodes)) {
            throw new ProductOutOfStockException("Few Products in your order are currently out of stock.");
        }
            orderRepository.save(order);
            return true;
    }

    public Order mapOrderRequestToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToOrderLineItems).toList();
        order.setOrderLineItemsList(orderLineItems);
        LocalDateTime orderDate = LocalDateTime.now();
        order.setOrderDate(orderDate);
        return order;
    }

    public OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }

    public List<OrderResponseDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::getOrderResponse).toList();
    }

    public OrderResponseDTO getOrderById(int id) throws NotFoundException {
        Optional<Order> orderById = orderRepository.findById(id);
        if (orderById.isEmpty()) {
            throw new NotFoundException("Order with id " + id + " is not available");
        } else {
            List<OrderResponseDTO> order = orderById.stream().map(this::getOrderResponse).toList();
            return order.get(0);
        }
    }

    private OrderResponseDTO getOrderResponse(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderNumber(order.getOrderNumber());
        orderResponseDTO.setOrderLineItemsList(order.getOrderLineItemsList());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        return orderResponseDTO;
    }

    public List<OrderResponseDTO> getOrderByDate(String date){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        try{
            LocalDate convertedDate = LocalDate.parse(date, dateTimeFormatter);
            startDateTime = convertedDate.atStartOfDay();
            endDateTime = convertedDate.atTime(23,59,59);

        }catch (DateTimeParseException de){
            throw new DateTimeParseException("Invalid date format. Expected format is yyyy-MM-dd. "
                    ,date ,de.getErrorIndex(), de );
        }

        List<Order> orders =  orderRepository.findAllByOrderDateBetween(startDateTime, endDateTime);

        return orders.stream().map(this::getOrderResponse).toList();

    }
}
