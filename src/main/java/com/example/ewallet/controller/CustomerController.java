package com.example.ewallet.controller;

import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Customer;
import com.example.ewallet.service.CustomerService;
import com.example.ewallet.service.CustomerServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/customer/")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @ApiOperation(value = "Creating customer")
    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws BusinessExcption {
        log.info("creating new customer: "+customer.toString());

        return customerService.createCustomer(customer);
    }


}
