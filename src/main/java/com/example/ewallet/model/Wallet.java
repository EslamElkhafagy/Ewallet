package com.example.ewallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @Column(name = "creation_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String objective;
    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "payer")
    @JsonIgnoreProperties
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> payerTransactions;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buyer")
    @JsonIgnoreProperties
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> buyerTransactions;

}
