package com.example.training.repository;

import com.example.training.model.jpa.ShoppingCartItem;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {

    Optional<ShoppingCartItem> findByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);
    void deleteByIdAndShoppingCartId(Long productId, Long shoppingCartId);
    void deleteByShoppingCartId(Long shoppingCartId);
}
