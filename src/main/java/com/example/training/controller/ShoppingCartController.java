package com.example.training.controller;

import com.example.training.model.domain.ProductDto;
import com.example.training.model.domain.ShoppingCartDto;
import com.example.training.service.ShoppingCartService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService service;

    public ShoppingCartController(ShoppingCartService service) {
        this.service = service;
    }

    @GetMapping("/{accountId}")
    public ShoppingCartDto getShoppingCart(@PathVariable Long accountId) {
        return service.getShoppingCart(accountId);
    }

    @PostMapping("/{shoppingCartId}")
    public ShoppingCartDto addProduct(@PathVariable Long shoppingCartId, @RequestBody ProductDto product) {
        return service.addToShoppingCart(shoppingCartId, product);
    }

    @DeleteMapping("/{shoppingCartId}/item/{cartItemId}")
    public void deleteFromShoppingCart(@PathVariable Long shoppingCartId, @PathVariable Long cartItemId) {
        service.deleteFromShoppingCart(shoppingCartId, cartItemId);
    }
}
