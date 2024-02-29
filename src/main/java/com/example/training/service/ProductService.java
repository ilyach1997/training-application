package com.example.training.service;

import com.example.training.converter.ProductConverter;
import com.example.training.model.domain.ProductDto;
import com.example.training.model.jpa.Product;
import com.example.training.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getById(Long id) {
        Product jpaProduct = productRepository.findById(id).orElseThrow();
        return ProductConverter.convertJpaProductToProduct(jpaProduct);
    }

    public Set<ProductDto> filter(String categoryName) {
        Set<ProductDto> jpaProducts = new HashSet<>();
        if (categoryName != null) {
            jpaProducts.addAll(convertJpaProducts(productRepository.findAllByCategoryName(categoryName)));
        } else {
            jpaProducts.addAll(convertJpaProducts(productRepository.findAll()));
        }
        return jpaProducts;
    }

    private List<ProductDto> convertJpaProducts(Iterable<Product> jpaProducts) {
        List<ProductDto> products = new ArrayList<>();
        jpaProducts.forEach(jpaProduct -> products.add(ProductConverter.convertJpaProductToProduct(jpaProduct)));
        return products;
    }
}
