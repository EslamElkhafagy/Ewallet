package com.example.ewallet.service;

import com.example.ewallet.dao.TransactionRepository;
import com.example.ewallet.dao.WalletRepository;
import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Customer;
import com.example.ewallet.model.Transaction;
import com.example.ewallet.model.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

/**
 * Test wallet Service
 * naming convention : methodName_input_output
 */
@SpringBootTest(classes = WalletService.class)
@RunWith(SpringRunner.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletServiceImpl walletService ;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Spy
    private TransactionServiceImpl transactionService;

    @Test
    public void createWallet_Wallet_Wallet() throws Exception {
        Mockito.doReturn(getWallet()).when(walletRepository).save(Matchers.anyObject());

        walletService.createWallet(getWallet());

    }

    @Test(expected = BusinessExcption.class)
    public void createWallet_Wallet_BusinessExcption() throws Exception {
        Mockito.doReturn(null).when(walletRepository).save(Matchers.anyObject());

        walletService.createWallet(getWallet());

    }

    @Test
    public void updateCustomerBalance_CreditWalletData_Transaction() throws Exception {
        Mockito.doReturn(getWallet()).when(walletRepository).save(Matchers.anyObject());
        Optional<Wallet> optionalWallet=Optional.of( getWallet());
        Mockito.doReturn(optionalWallet).when(walletRepository).findByCode(Matchers.anyString());
        Mockito.doReturn(new Transaction()).when(transactionRepository).save(Matchers.anyObject());

        walletService.updateCustomerBalance("","CREDIT",100);

    }

    @Test
    public void updateCustomerBalance_DebitWalletData_Transaction() throws Exception {
        Mockito.doReturn(getWallet()).when(walletRepository).save(Matchers.anyObject());
        Optional<Wallet> optionalWallet=Optional.of( getWallet());
        Mockito.doReturn(optionalWallet).when(walletRepository).findByCode(Matchers.anyString());
        Mockito.doReturn(new Transaction()).when(transactionRepository).save(Matchers.anyObject());

        walletService.updateCustomerBalance("","DEBIT",100);

    }

    @Test(expected = BusinessExcption.class)
    public void invalidDebit_DebitWalletData_BusinessExcption() throws Exception {
        Mockito.doReturn(getWallet()).when(walletRepository).save(Matchers.anyObject());
        Optional<Wallet> optionalWallet=Optional.of( getWallet());
        Mockito.doReturn(optionalWallet).when(walletRepository).findByCode(Matchers.anyString());
        Mockito.doReturn(new Transaction()).when(transactionRepository).save(Matchers.anyObject());

        walletService.updateCustomerBalance("","DEBIT",1000);

    }


    private Wallet getWallet(){
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
        return wallet;
    }


}
