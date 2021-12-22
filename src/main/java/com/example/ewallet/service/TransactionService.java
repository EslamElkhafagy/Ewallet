package com.example.ewallet.service;

import com.example.ewallet.dto.WalletTransactionDTO;
import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Transaction;
import org.springframework.http.ResponseEntity;

public interface TransactionService {

    ResponseEntity<Transaction> createTransaction(Transaction transaction) throws BusinessExcption;

    String generateTransactionId();
    Transaction transferBalance(WalletTransactionDTO walletTransactionDTO)throws BusinessExcption;
    }
