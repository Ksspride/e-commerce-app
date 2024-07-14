package com.kss.customer.service;

import com.kss.customer.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customer);

    List<CustomerDto> findAllCustomers();

    Boolean existsById(String customerId);

    CustomerDto findById(String customerId);

    void deleteCustomer(String customerId);
}
