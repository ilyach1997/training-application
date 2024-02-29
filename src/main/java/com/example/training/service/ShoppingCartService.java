package com.example.training.service;

import com.example.training.converter.ProductConverter;
import com.example.training.converter.ShoppingCartConverter;
import com.example.training.model.domain.ProductDto;
import com.example.training.model.domain.ShoppingCartDto;
import com.example.training.model.jpa.Account;
import com.example.training.model.jpa.Category;
import com.example.training.model.jpa.Product;
import com.example.training.model.jpa.ShoppingCart;
import com.example.training.model.jpa.ShoppingCartItem;
import com.example.training.repository.CategoryRepository;
import com.example.training.repository.ShoppingCartItemRepository;
import com.example.training.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final CategoryRepository categoryRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ShoppingCartItemRepository shoppingCartItemRepository,
                               CategoryRepository categoryRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public ShoppingCartDto getShoppingCart(Long accountId) {
        ShoppingCart jpaShoppingCart = shoppingCartRepository.findShoppingCartByAccountId(accountId)
                                                             .orElseGet(() -> shoppingCartRepository.save(new ShoppingCart(new Account(accountId))));
        return ShoppingCartConverter.convertJpaShoppingCartToShoppingCart(jpaShoppingCart);
    }

    public ShoppingCartDto addToShoppingCart(Long shoppingCartId, ProductDto product) {
        ShoppingCart jpaShoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow();
        shoppingCartItemRepository.save(getItem(shoppingCartId, product, jpaShoppingCart));
        return ShoppingCartConverter.convertJpaShoppingCartToShoppingCart(jpaShoppingCart);
    }

    private ShoppingCartItem getItem(Long shoppingCartId, ProductDto product, ShoppingCart jpaShoppingCart) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, product.id()).orElseGet(() -> {
            Category category = categoryRepository.findByName(product.categoryName()).orElseThrow();
            Product jpaProduct = ProductConverter.convertProductToJpaProduct(product, category);
            ShoppingCartItem item = new ShoppingCartItem();
            item.setQuantity(product.quantity());
            item.setShoppingCart(jpaShoppingCart);
            item.setProduct(jpaProduct);
            return item;
        });
        shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + product.quantity());
        return shoppingCartItem;
    }

    @Transactional
    public void deleteFromShoppingCart(Long shoppingCartId, Long cartItemId) {
        shoppingCartItemRepository.deleteByIdAndShoppingCartId(cartItemId, shoppingCartId);
    }
}
