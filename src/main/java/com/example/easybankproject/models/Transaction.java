package com.example.easybankproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "receiver_account_id")
    private int receiverAccountId;

    @Column(name = "sender_account_id")
    private int senderAccountId;

    private double amount;

    private LocalDateTime timestamp;

    private String message;

    @Column(name = "message_ja")
    private String messageJapanese;

    @Column(name = "message_ko")
    private String messageKorean;

    @Column(name = "message_ar")
    private String messageArabic;

    @Column(name = "message_es")
    private String messageSpanish;

    @Column(name = "message_fin")
    private String messageFinnish;

}