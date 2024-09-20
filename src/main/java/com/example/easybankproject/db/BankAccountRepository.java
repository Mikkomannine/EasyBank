package com.example.easybankproject.db;


import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    Optional<BankAccount> findByUser(User user);
}
