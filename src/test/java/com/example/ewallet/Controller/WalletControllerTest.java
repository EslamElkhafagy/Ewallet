package com.example.ewallet.Controller;


import com.example.ewallet.controller.WalletController;
import com.example.ewallet.dao.WalletRepository;
import com.example.ewallet.model.Customer;
import com.example.ewallet.model.Wallet;
import com.example.ewallet.service.WalletServiceImpl;
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
import java.util.Optional;

/**
 * Test wallet Controller
 * naming convention : methodName_input_output
 */
@SpringBootTest(classes = WalletControllerTest.class)
@RunWith(SpringRunner.class)
public class WalletControllerTest {

    private final String BASE_URL="/api/wallet/";
    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;
    MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    public void createWallet_Wallet_Wallet() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();

        Mockito.doReturn(getWallet()).when(walletService).createWallet(Matchers.anyObject());
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getWallet())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateWallet_WalletData_Wallet() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();

        Optional<Wallet> optionalWallet=Optional.of( getWallet().getBody());
        Mockito.doReturn(optionalWallet).when(walletRepository).findByCode(Matchers.anyString());
        Mockito.doReturn(getWallet()).when(walletService).updateCustomerBalance(Matchers.anyString(),Matchers.anyString(),Matchers.anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/wallet/?walletCode=ES102035&transactionType=DEBIT&amount=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getWallet())))
                        .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateWallet_WalletData_BadRequest() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();

        Optional<Wallet> optionalWallet=Optional.of( getWallet().getBody());
        Mockito.doReturn(optionalWallet).when(walletRepository).findByCode(Matchers.anyString());
        Mockito.doReturn(getWallet()).when(walletService).updateCustomerBalance(Matchers.anyString(),Matchers.anyString(),Matchers.anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getWallet())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    private ResponseEntity<Wallet> getWallet(){
        Wallet wallet = new Wallet();
        wallet.setBalance(200.0);
        wallet.setCode("ES201030");
        wallet.setCreationDate(new Date());
        wallet.setObjective("Personal Account");

        Customer customer = new Customer();
        customer.setEmail("customer@gmail.com");
        customer.setFirstName("Eslam");
        customer.setJoinDate(new Date());
        customer.setLastName("Ahmed");
        customer.setPassword("12sad21");
        customer.setPhone("+201012987074");

        wallet.setCustomer(customer);
        return new ResponseEntity(wallet, HttpStatus.CREATED);
    }

}
