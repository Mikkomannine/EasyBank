package com.example.easybankproject.db;

import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.models.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
}
