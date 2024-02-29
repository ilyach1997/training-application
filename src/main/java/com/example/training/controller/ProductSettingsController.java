package com.example.training.controller;

import com.example.training.model.domain.ProductDto;
import com.example.training.service.admin.ProductSettingsService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/settings/product")
public class ProductSettingsController {

    private final ProductSettingsService productSettingsService;

    public ProductSettingsController(ProductSettingsService productSettingsService) {
        this.productSettingsService = productSettingsService;
    }

    @PutMapping
    public ProductDto changeProduct(@RequestBody ProductDto product) {
        return productSettingsService.changeProduct(product);
    }
}
