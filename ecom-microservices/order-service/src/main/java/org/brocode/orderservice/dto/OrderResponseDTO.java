package org.brocode.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.brocode.orderservice.model.OrderLineItems;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDTO {
    private String orderNumber;
    private List<OrderLineItems> orderLineItemsList;
    private LocalDateTime orderDate;
}
