package com.metabuild.microservice.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.metabuild.microservice.product.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	List<Product> findByCategory(String category);
}
