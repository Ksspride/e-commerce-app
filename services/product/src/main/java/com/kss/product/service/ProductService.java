package com.kss.product.service;

import com.kss.product.dto.ProductDto;
import com.kss.product.dto.ProductPurchaseResponseDto;

import java.util.List;

public interface ProductService {
    Integer createProduct(ProductDto request);

    List<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseResponseDto> request);

    ProductDto findById(Integer productId);

    List<ProductDto> findAll();
}
