package org.brocode.orderservice.service;


import org.brocode.orderservice.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service

public class InventoryService {

    private final WebClient.Builder webClient;
    @Value("${inventory.service.url}")
    private String inventoryBaseUrl;

    public InventoryService(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    public boolean isStockAvailable(List<String> skuCodes ) {

        InventoryResponse[] inventoryResponses = webClient.build()
                .get()
                .uri(inventoryBaseUrl+"/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        return Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

    }
}
