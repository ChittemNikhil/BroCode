package org.brocode.inventoryservice.service;


import lombok.RequiredArgsConstructor;
import org.brocode.inventoryservice.dto.InventoryResponse;
import org.brocode.inventoryservice.dto.ProductAvailabilityResponse;
import org.brocode.inventoryservice.model.Inventory;
import org.brocode.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {

        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
                InventoryResponse.builder().skuCode(inventory.getSkuCode())
                        .inStock(inventory.getQuantity() > 0).build()).toList();
    }

    public List<ProductAvailabilityResponse> isAvailable(List<String> skuCode) {

        List<Inventory> inventoryList = new ArrayList<>();
        try {
            inventoryList = inventoryRepository.findBySkuCodeIn(skuCode);
        } catch (Exception e) {
            log.error("Some of the products are not available. {}", e.getMessage());
        }
        return inventoryList.stream().map(this::mapToProductAvailability).toList();

    }

    public ProductAvailabilityResponse mapToProductAvailability(Inventory inventory) {
        ProductAvailabilityResponse productAvailabilityResponse = new ProductAvailabilityResponse();
        productAvailabilityResponse.setSkuCode(inventory.getSkuCode());
        productAvailabilityResponse.setProductQuantity(inventory.getQuantity());

        if (inventory.getQuantity() == 0) {
            productAvailabilityResponse.setInStock(false);
            productAvailabilityResponse.setMessage("Out Of Stock");
        } else {
            productAvailabilityResponse.setInStock(true);
            if(inventory.getQuantity() > 20){
                productAvailabilityResponse.setMessage("In stock");
            }else if(inventory.getQuantity() < 10){
                productAvailabilityResponse.setMessage("Order soon only " + inventory.getQuantity() + " left !!");
            }else if(inventory.getQuantity() <20){
                productAvailabilityResponse.setMessage("Order soon only few items left !!");
            }
        }
        return productAvailabilityResponse;
    }

}
