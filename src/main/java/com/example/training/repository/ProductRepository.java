package com.example.training.repository;

import com.example.training.model.jpa.Product;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Set<Product> findAllByCategoryName(String categoryName);
}
