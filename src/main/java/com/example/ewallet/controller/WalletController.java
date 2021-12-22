package com.example.ewallet.controller;

import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Transaction;
import com.example.ewallet.model.Wallet;
import com.example.ewallet.service.WalletServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wallet/")
@Slf4j
public class WalletController {

    @Autowired
    private WalletServiceImpl walletService;

    @ApiOperation(value = "Creating wallet")
    @PostMapping("")
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) throws BusinessExcption {
        log.info("creating new wallet: "+wallet.toString());

        return walletService.createWallet(wallet);
    }

    @ApiOperation(value = "update Wallet")
    @PutMapping("")
    public ResponseEntity<Transaction> updateBalance(@RequestParam String walletCode, @RequestParam String transactionType, @RequestParam Double amount) throws BusinessExcption {
        log.info("updating wallet: {0} with type : {1} and amount : {2}",walletCode,transactionType,amount);

        return walletService.updateCustomerBalance(walletCode,transactionType,amount);
    }

}
