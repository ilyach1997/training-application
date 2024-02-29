package com.example.training.repository;

import com.example.training.model.jpa.ShoppingCart;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findShoppingCartByAccountId(Long accountId);
}
