package com.example.ewallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String transactionId;

    private Date creationDate;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "walletPayer_id", nullable = false)
    private Wallet payer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "walletBuyer_id", nullable = false)
    private Wallet buyer;


}
