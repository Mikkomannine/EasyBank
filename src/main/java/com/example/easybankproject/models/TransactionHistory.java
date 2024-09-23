package com.example.easybankproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactionhistory")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_history_id;
    private int transaction_id;
    private int senderAccount;
    private int receiverAccount;
    private LocalDateTime timestamp;
}
