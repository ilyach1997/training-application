package com.example.training.model.domain;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, String description, int quantity, BigDecimal cost, String photoUrl, String pdfUrl, String categoryName) {

}
