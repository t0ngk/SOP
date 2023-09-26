package com.t0ng.lab07;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

    @Query("{ 'productName' : ?0 }")
    public Product findByProductName(String productName);
}
