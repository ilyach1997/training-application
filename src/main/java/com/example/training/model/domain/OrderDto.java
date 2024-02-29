package com.example.training.model.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record OrderDto(BigDecimal totalCost, List<ProductDto> products) {

    public OrderDto {
        products = new ArrayList<>();
    }
}
