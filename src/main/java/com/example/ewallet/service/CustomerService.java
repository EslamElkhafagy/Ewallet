package com.example.ewallet.service;

import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<Customer> createCustomer(Customer customer) throws BusinessExcption;
}
