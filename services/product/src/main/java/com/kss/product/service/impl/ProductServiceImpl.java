package com.kss.product.service.impl;

import com.kss.product.dto.ProductDto;
import com.kss.product.dto.ProductPurchaseRequestDto;
import com.kss.product.dto.ProductPurchaseResponseDto;
import com.kss.product.entity.Product;
import com.kss.product.exceptions.ProductPurchaseException;
import com.kss.product.exceptions.RecordNotFoundException;
import com.kss.product.repo.ProductRepository;
import com.kss.product.service.ProductService;
import com.kss.product.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    public List<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseRequestDto> request) {

        var productIds = request
                .stream()
                .map(ProductPurchaseRequestDto::productId)
                .toList();
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequestDto::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponseDto>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            ProductPurchaseResponseDto responseDto = new ProductPurchaseResponseDto();
            responseDto.setProductId(product.getId());
            responseDto.setName(product.getName());
            responseDto.setQuantity(productRequest.quantity());
            responseDto.setPrice(product.getPrice());
            responseDto.setDescription(product.getDescription());

            purchasedProducts.add(responseDto);        }
        return purchasedProducts;
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
