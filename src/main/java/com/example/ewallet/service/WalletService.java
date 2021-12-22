package com.example.ewallet.service;

import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Wallet;
import org.springframework.http.ResponseEntity;

public interface WalletService {

    ResponseEntity<Wallet> createWallet(Wallet wallet) throws BusinessExcption;
}
