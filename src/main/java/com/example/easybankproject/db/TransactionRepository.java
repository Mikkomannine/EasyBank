package com.example.easybankproject.db;

import com.example.easybankproject.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllBySenderAccountIdOrReceiverAccountId(int senderAccountId, int receiverAccountId);
}

