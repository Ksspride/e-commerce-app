package com.kss.customer.service.impl;

import com.kss.customer.dto.AddressDto;
import com.kss.customer.dto.CustomerDto;
import com.kss.customer.entity.Customer;
import com.kss.customer.exceptions.RecordNotFoundException;
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
        return ObjectMapperUtils.mapAll(repository.findAll(), CustomerDto.class);
    }

    @Override
    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    @Override
    public CustomerDto findById(String customerId) {
        CustomerDto customerDto =new CustomerDto();
        Customer savedCustomer = repository.findById(customerId).orElseThrow(()-> new RecordNotFoundException("Customer with request id not found"));
        customerDto.setId(savedCustomer.getId());
        customerDto.setEmail(savedCustomer.getEmail());
        customerDto.setFirstName(savedCustomer.getFirstName());
        customerDto.setLastName(savedCustomer.getLastName());
        customerDto.setAddress(ObjectMapperUtils.map(savedCustomer.getAddress(), AddressDto.class));
        return customerDto;
    }

    @Override
    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);

    }
}
