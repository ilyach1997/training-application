package com.example.training.converter;

import static java.util.stream.Collectors.toList;

import com.example.training.model.domain.ProductDto;
import com.example.training.model.domain.ShoppingCartDto;
import com.example.training.model.jpa.ShoppingCart;
import java.util.List;

public class ShoppingCartConverter {

    private ShoppingCartConverter() {
    }

    public static ShoppingCartDto convertJpaShoppingCartToShoppingCart(ShoppingCart jpaShoppingCart) {
        List<ProductDto> products = jpaShoppingCart.getItems()
                                                   .stream()
                                                   .map(item -> ProductConverter.convertJpaProductToProduct(item.getProduct(), item.getQuantity()))
                                                   .collect(toList());
        return new ShoppingCartDto(jpaShoppingCart.getId(), products, jpaShoppingCart.getTotalCost());
    }
}
