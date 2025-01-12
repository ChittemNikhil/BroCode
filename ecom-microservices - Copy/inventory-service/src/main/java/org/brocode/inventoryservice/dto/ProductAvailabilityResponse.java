package org.brocode.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAvailabilityResponse {

    private int productQuantity;
    private String message;
    private boolean inStock;
    private String skuCode;

}
