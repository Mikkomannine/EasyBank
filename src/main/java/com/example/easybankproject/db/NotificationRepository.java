package com.example.easybankproject.db;

import com.example.easybankproject.models.Notification;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
    List<Notification> findByTransaction(Transaction transaction);
    List<Notification> findByUserAndTransaction(User user, Transaction transaction);
    List<Notification> findByUserAndTimestampAfter(User user, LocalDateTime timestamp);
    List<Notification> findByUserAndTimestampBefore(User user, LocalDateTime timestamp);
    List<Notification> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Notification> findByTransactionAndTimestampAfter(Transaction transaction, LocalDateTime timestamp);
    List<Notification> findByTransactionAndTimestampBefore(Transaction transaction, LocalDateTime timestamp);
    List<Notification> findByTransactionAndTimestampBetween(Transaction transaction, LocalDateTime start, LocalDateTime end);
    List<Notification> findByUserAndTransactionAndTimestampAfter(User user, Transaction transaction, LocalDateTime timestamp);
    List<Notification> findByUserAndTransactionAndTimestampBefore(User user, Transaction transaction, LocalDateTime timestamp);
    List<Notification> findByUserAndTransactionAndTimestampBetween(User user, Transaction transaction, LocalDateTime start, LocalDateTime end);
}
