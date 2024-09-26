package org.brocode.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.brocode.orderservice.dto.OrderLineItemsDto;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
