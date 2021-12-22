package com.example.ewallet.Controller;

import com.example.ewallet.controller.CustomerController;
import com.example.ewallet.dao.WalletRepository;
import com.example.ewallet.model.Customer;
import com.example.ewallet.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

/**
 * Test customer Controller
 * naming convention : methodName_input_output
 */
@SpringBootTest(classes = CustomerControllerTest.class)
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    private final String BASE_URL="/api/customer/";
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private WalletRepository walletRepository;
    MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void createCustomer_Customer_Customer() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();

        Mockito.doReturn(getCustomer()).when(customerService).createCustomer(Matchers.anyObject());
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCustomer())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    private ResponseEntity<Customer> getCustomer(){
        Customer customer = new Customer();
        customer.setEmail("customer@gmail.com");
        customer.setFirstName("Eslam");
        customer.setJoinDate(new Date());
        customer.setLastName("Ahmed");
        customer.setPassword("12sad21");
        customer.setPhone("+201012987074");

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}
