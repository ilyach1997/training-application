package com.example.training.repository;

import com.example.training.model.jpa.Category;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
