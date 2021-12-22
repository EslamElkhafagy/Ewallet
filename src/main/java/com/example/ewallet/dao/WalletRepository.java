package com.example.ewallet.dao;

import com.example.ewallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findById(Long id);
    Optional<Wallet> findByCode(String code);

}
