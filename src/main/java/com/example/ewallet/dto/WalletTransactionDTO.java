package com.example.ewallet.dto;

import com.example.ewallet.model.TransactionType;
import lombok.Data;

@Data
public class WalletTransactionDTO {

        private String payerWalletCode;
        private String buyerWalletCode;
        private TransactionType transactionType;
        private Double amount;
        private String description;


}
