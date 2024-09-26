package org.brocode.productservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brocode.productservice.dto.ProductRequest;
import org.brocode.productservice.dto.ProductResponse;
import org.brocode.productservice.model.Product;
import org.brocode.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Created product: {}", product.getId());

    }

    public List<ProductResponse> getAllProducts(){
       List<Product> products = productRepository.findAll();

       return products.stream().map(this::mapToProductResponse).toList();

    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().
                        id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build();
    }
}
