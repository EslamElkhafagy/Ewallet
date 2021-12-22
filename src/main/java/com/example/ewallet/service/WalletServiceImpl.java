package com.example.ewallet.service;

import com.example.ewallet.dao.TransactionRepository;
import com.example.ewallet.dao.WalletRepository;
import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Transaction;
import com.example.ewallet.model.TransactionType;
import com.example.ewallet.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Wallet> createWallet(Wallet wallet) throws BusinessExcption {
        String message="";
        Wallet savedWallet;
        try {
            savedWallet = walletRepository.save(wallet);
            log.info("wallet created with id :" + savedWallet.getId());
        }catch (Exception e){
            message="Error occurred while saving wallet or may be exist";
            log.error(message);
            throw new BusinessExcption(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(savedWallet, HttpStatus.CREATED);
    }


    public ResponseEntity<Transaction> updateCustomerBalance(String walletCode,String transactionType, double amount )throws BusinessExcption {

        Wallet payerWallet = walletRepository.findByCode(walletCode).get();

        if(payerWallet == null )
            throw new BusinessExcption("customer data not valid", HttpStatus.NOT_FOUND);

        if(TransactionType.CREDIT.name().equals(transactionType)){

            payerWallet.setBalance(payerWallet.getBalance()+ amount);

        }else if(TransactionType.DEBIT.name().equals(transactionType)){

            if(payerWallet.getBalance()<amount)
                throw new BusinessExcption("insufficient balance",HttpStatus.FORBIDDEN);

            payerWallet.setBalance(payerWallet.getBalance()- amount);

        }
        walletRepository.save(payerWallet);
        return saveTransaction(payerWallet,transactionType,amount);

    }



    private ResponseEntity<Transaction> saveTransaction(Wallet payerWallet,String transactionType, double amount ){
        Transaction transaction= new Transaction();

        transaction.setTransactionId(transactionService.generateTransactionId());
        transaction.setTransactionType(TransactionType.valueOf(transactionType));
        transaction.setAmount(amount);
        transaction.setDescription(transactionType+" charge");
        transaction.setBuyer(payerWallet);
        transaction.setPayer(payerWallet);
        transaction.setCreationDate(new Date());

        return new ResponseEntity(transactionRepository.save(transaction),HttpStatus.OK);

    }


}
