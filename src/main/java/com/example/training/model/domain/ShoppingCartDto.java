package com.example.training.model.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record ShoppingCartDto(Long id, List<ProductDto> products, BigDecimal totalCost) {

    public ShoppingCartDto {
        products = new ArrayList<>();
    }
}
