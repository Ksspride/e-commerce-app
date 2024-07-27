package com.kss.order.product;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    @PostMapping("/purchase")
    public List<ProductPurchaseResponseDto> purchaseProducts(
            @RequestBody List<ProductPurchaseRequestDto> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<ProductPurchaseRequestDto>> requestEntity= new HttpEntity<>(requestBody,headers);
        ParameterizedTypeReference<List<ProductPurchaseResponseDto>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<ProductPurchaseResponseDto>> responseEntity = restTemplate.exchange(
                productUrl +"/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        if(responseEntity.getStatusCode().isError()){
            throw new IllegalArgumentException("An error occurred while processing the product purchase ");
        }
        return responseEntity.getBody();
    }
}
