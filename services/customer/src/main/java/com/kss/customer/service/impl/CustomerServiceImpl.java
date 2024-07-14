package com.kss.customer.service.impl;

import com.kss.customer.dto.CustomerDto;
import com.kss.customer.entity.Customer;
import com.kss.customer.repo.CustomerRepository;
import com.kss.customer.service.CustomerService;
import com.kss.customer.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        Customer tobeSavedCustomer = ObjectMapperUtils.map(customer, Customer.class);
        Customer savedCustomer = repository.save(tobeSavedCustomer);
        log.info("Customer created successfully {}", savedCustomer.getFirstName());
        return ObjectMapperUtils.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        return null;
    }

    @Override
    public Boolean existsById(String customerId) {
        return null;
    }

    @Override
    public CustomerDto findById(String customerId) {
        return null;
    }

    @Override
    public void deleteCustomer(String customerId) {

    }
}
