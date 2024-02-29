package com.example.training.service.admin;

import static com.example.training.constant.Messages.ExceptionMessages.CATEGORY_DOES_NOT_EXISTS;

import com.example.training.converter.ProductConverter;
import com.example.training.model.domain.ProductDto;
import com.example.training.model.jpa.Category;
import com.example.training.model.jpa.Product;
import com.example.training.repository.CategoryRepository;
import com.example.training.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductSettingsService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductSettingsService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDto changeProduct(ProductDto productDto) {
        Category category = categoryRepository.findByName(productDto.categoryName())
                                              .orElseThrow(() -> new IllegalArgumentException(CATEGORY_DOES_NOT_EXISTS));
        Product product = ProductConverter.convertProductToJpaProduct(productDto, category);
        Product save = productRepository.save(product);
        return ProductConverter.convertJpaProductToProduct(save);
    }
}
