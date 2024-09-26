package org.brocode.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.brocode.productservice.model.Product;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
