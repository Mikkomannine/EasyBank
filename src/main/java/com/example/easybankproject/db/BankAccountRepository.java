package com.example.easybankproject.db;


import com.example.easybankproject.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    BankAccount findBankAccountById(Long accountId);
}
