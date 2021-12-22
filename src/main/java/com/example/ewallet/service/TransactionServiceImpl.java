package com.example.ewallet.service;

import com.example.ewallet.dao.TransactionRepository;
import com.example.ewallet.dao.WalletRepository;
import com.example.ewallet.dto.WalletTransactionDTO;
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
import java.util.Random;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Transaction> createTransaction(Transaction transaction) throws BusinessExcption {
        String message="";
        Transaction savedTransaction;
        try {
            savedTransaction = transactionRepository.save(transaction);
            log.info("transaction saved with id :" + savedTransaction.getTransactionId());
        }catch (Exception e){
            message="Error occurred while saving transaction or may be exist";
            log.error(message);
            log.error(e.getMessage());
            throw new BusinessExcption(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @Override // should be more complex
    public String generateTransactionId() {

        Random rand = new Random();
        double randNumber = rand.nextDouble();

        return String.valueOf(randNumber*100000000);
    }


    @Override
    public Transaction transferBalance(WalletTransactionDTO walletTransactionDTO)throws BusinessExcption {

        Wallet payerWallet = walletRepository.findByCode(walletTransactionDTO.getPayerWalletCode()).get();
        Wallet buyerWallet = walletRepository.findByCode(walletTransactionDTO.getBuyerWalletCode()).get();

        if(payerWallet == null || buyerWallet ==null)
            throw new BusinessExcption("customer data not valid", HttpStatus.NOT_FOUND);
        if(TransactionType.TRANSFER.name().equalsIgnoreCase(String.valueOf(walletTransactionDTO.getTransactionType()))){


            if(payerWallet.getBalance()<feesCharged(walletTransactionDTO.getAmount()))
                throw new BusinessExcption("insufficient balance",HttpStatus.FORBIDDEN);

            payerWallet.setBalance(payerWallet.getBalance()-feesCharged(walletTransactionDTO.getAmount()));
            buyerWallet.setBalance(buyerWallet.getBalance()+walletTransactionDTO.getAmount());

            walletRepository.save(payerWallet);
            walletRepository.save(buyerWallet);
        }else {
                throw new BusinessExcption("invalid path ",HttpStatus.FORBIDDEN);
        }
    return saveTransferTransaction(buyerWallet,payerWallet,walletTransactionDTO);
    }


    private Transaction saveTransferTransaction(Wallet buyerWallet,Wallet payerWallet,WalletTransactionDTO walletTransactionDTO){
        Transaction transaction= new Transaction();

        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionType(walletTransactionDTO.getTransactionType());
        transaction.setAmount(walletTransactionDTO.getAmount());
        transaction.setDescription(walletTransactionDTO.getDescription());
        transaction.setBuyer(buyerWallet);
        transaction.setPayer(payerWallet);
        transaction.setCreationDate(new Date());

        return transactionRepository.save(transaction);

    }

    /*
    * fees amount assuming from payer ( sender )
    * */
    private Double feesCharged(double amount){

        return amount+(amount*0.01);
    }

}
