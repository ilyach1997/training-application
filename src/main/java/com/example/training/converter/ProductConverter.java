package com.example.training.converter;

import com.example.training.model.domain.ProductDto;
import com.example.training.model.jpa.Category;
import com.example.training.model.jpa.Product;

public class ProductConverter {

    private ProductConverter() {
    }

    public static Product convertProductToJpaProduct(ProductDto product, Category category) {
        Product jpaProduct = new Product();
        jpaProduct.setId(product.id());
        jpaProduct.setName(product.name());
        jpaProduct.setCost(product.cost());
        jpaProduct.setPdfUrl(product.pdfUrl());
        jpaProduct.setPhotoUrl(product.photoUrl());
        jpaProduct.setDescription(product.description());
        jpaProduct.setCategory(category);
        return jpaProduct;
    }

    public static ProductDto convertJpaProductToProduct(Product jpaProduct) {
        return convertJpaProductToProduct(jpaProduct, 1);
    }

    public static ProductDto convertJpaProductToProduct(Product jpaProduct, int quantity) {
        return new ProductDto(jpaProduct.getId(),
                              jpaProduct.getName(),
                              jpaProduct.getDescription(),
                              quantity,
                              jpaProduct.getCost(),
                              jpaProduct.getPhotoUrl(),
                              jpaProduct.getPdfUrl(),
                              jpaProduct.getCategory().getName());
    }
}
