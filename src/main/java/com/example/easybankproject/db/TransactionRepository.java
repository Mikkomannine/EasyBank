package com.example.easybankproject.db;

import com.example.easybankproject.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByTransactionId(int transactionId);
    Optional<Transaction> findByReceiverAccountId(int ReceiverAccountId);
    Optional<Transaction> findBySenderAccountId(int senderAccountId);

    List<Transaction> findAllBySenderAccountIdOrReceiverAccountId(int senderAccountId, int receiverAccountId);
}

