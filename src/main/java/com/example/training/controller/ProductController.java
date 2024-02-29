package com.example.training.controller;

import com.example.training.model.domain.ProductDto;
import com.example.training.service.ProductService;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/filter")
    public Set<ProductDto> filterProducts(@RequestParam String categoryName) {
        return service.filter(categoryName);
    }
}
