package org.brocode.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.brocode.inventoryservice.dto.InventoryResponse;
import org.brocode.inventoryservice.dto.ProductAvailabilityResponse;
import org.brocode.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService  inventoryService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);

    }

    @GetMapping("/products/availability")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductAvailabilityResponse> isAvailable(@RequestParam List<String> skuCode){
        return inventoryService.isAvailable(skuCode);
    }
}
