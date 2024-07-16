package com.kss.product.service.impl;

import com.kss.product.dto.ProductDto;
import com.kss.product.dto.ProductPurchaseResponseDto;
import com.kss.product.entity.Product;
import com.kss.product.exceptions.RecordNotFoundException;
import com.kss.product.repo.ProductRepository;
import com.kss.product.service.ProductService;
import com.kss.product.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Integer createProduct(ProductDto request) {
        Product product = ObjectMapperUtils.map(request, Product.class);
        Product savedProduct = repository.save(product);
        log.info("Product is saved successfully");
        return savedProduct.getId();
    }

    @Override
    public List<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseResponseDto> request) {
        return null;
    }

    @Override
    public ProductDto findById(Integer productId) {
        Product savedProduct = repository.findById(productId).orElseThrow(()-> new RecordNotFoundException("Product not found"));
        log.info("Returning the product {}", savedProduct.getName());
        return ObjectMapperUtils.map(savedProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> findAll() {
        return repository.findAll()
                .stream()
                .map(a->ObjectMapperUtils.map(a, ProductDto.class))
                .toList();
    }
}
