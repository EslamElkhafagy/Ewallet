package com.example.ewallet.service;

import com.example.ewallet.dao.CustomerRepository;
import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<Customer> createCustomer(Customer customer) throws BusinessExcption {
        String message="";
        Customer savedCustomer;
        try {
            savedCustomer = customerRepository.save(customer);
            log.info("customer saved with id :" + savedCustomer.getId());
        }catch (Exception e){
            message="Error occurred while saving customer or may be exist";
            log.error(message);
            throw new BusinessExcption(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);


    }
}
