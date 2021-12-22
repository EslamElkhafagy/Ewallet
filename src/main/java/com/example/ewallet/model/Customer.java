package com.example.ewallet.model;

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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    private Date joinDate;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true,mappedBy = "customer")
    private List<Wallet> wallets;
}
