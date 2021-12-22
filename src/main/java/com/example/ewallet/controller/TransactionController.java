package com.example.ewallet.controller;


import com.example.ewallet.dto.WalletTransactionDTO;
import com.example.ewallet.exception.BusinessExcption;
import com.example.ewallet.model.Transaction;
import com.example.ewallet.service.TransactionServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/funding-sources/")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @ApiOperation(value = "Creating Transaction")
    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws BusinessExcption {
        log.info("creating new transaction: "+transaction.toString());

        return transactionService.createTransaction(transaction);
    }

    @ApiOperation(value = "transfer amount")
    @PutMapping("")
    public ResponseEntity<Transaction> transferAmount(@RequestBody WalletTransactionDTO walletTransactionDTO) throws BusinessExcption {
        log.info("creating new transaction: "+walletTransactionDTO.toString());

        return new ResponseEntity<>(transactionService.transferBalance(walletTransactionDTO), HttpStatus.OK);
    }
}
