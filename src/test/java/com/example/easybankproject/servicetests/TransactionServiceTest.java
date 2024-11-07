package com.example.easybankproject.servicetests;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.services.NotificationService;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TransactionServiceTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageSource messageSource;  // Inject MessageSource for localization

    public String createTransaction(Transaction transaction, Locale locale) {
        // Attempt to retrieve sender and receiver accounts, or throw a localized error
        BankAccount senderAccount = bankAccountRepository.findByBankAccountId(transaction.getSenderAccountId())
                .orElseThrow(() -> new RuntimeException(messageSource.getMessage("error.account.not.found", null, "Sender or receiver account not found.", locale)));
        BankAccount receiverAccount = bankAccountRepository.findByBankAccountId(transaction.getReceiverAccountId())
                .orElseThrow(() -> new RuntimeException(messageSource.getMessage("error.account.not.found", null, "Sender or receiver account not found.", locale)));

        // Check for sufficient funds in sender's account
        if (senderAccount.getBalance().compareTo(BigDecimal.valueOf(transaction.getAmount())) < 0) {
            return messageSource.getMessage("error.insufficient.funds", null, "Insufficient funds.", locale);
        }

        // Deduct and add funds
        senderAccount.setBalance(senderAccount.getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())));
        receiverAccount.setBalance(receiverAccount.getBalance().add(BigDecimal.valueOf(transaction.getAmount())));
        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(receiverAccount);

        // Record the transaction
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        // Send localized notifications
        String toNotification = messageSource.getMessage("transaction.notification.to", new Object[]{transaction.getAmount(), transaction.getReceiverAccountId()}, locale);
        String fromNotification = messageSource.getMessage("transaction.notification.from", new Object[]{transaction.getAmount(), transaction.getSenderAccountId()}, locale);
        notificationService.createNotification(senderAccount.getUser(), transaction, toNotification);
        notificationService.createNotification(receiverAccount.getUser(), transaction, fromNotification);

        // Return success message with localized transaction ID message
        return messageSource.getMessage("created.transaction", new Object[]{transaction.getTransactionId()}, "Transaction created successfully. ID: ", locale);
    }

    public int getSenderId(String token) {
        String username = jwtUtil.extractUsername(token);
        return bankAccountRepository.findByUser(userRepository.findByUsername(username))
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getBankAccountId();
    }

    public List<Transaction> getTransactions(String token) {
        String username = jwtUtil.extractUsername(token);
        BankAccount bankAccount = bankAccountRepository.findByUser(userRepository.findByUsername(username))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findAllBySenderAccountIdOrReceiverAccountId(bankAccount.getBankAccountId(), bankAccount.getBankAccountId());
    }
}
