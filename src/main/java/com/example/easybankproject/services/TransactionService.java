package com.example.easybankproject.services;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Transaction;
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
public class TransactionService {

    private BankAccountRepository bankAccountRepository;

    private TransactionRepository transactionRepository;

    private NotificationService notificationService;

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    private MessageSource messageSource;

    @Autowired
    public TransactionService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, NotificationService notificationService, UserRepository userRepository, JwtUtil jwtUtil, MessageSource messageSource) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.messageSource = messageSource;
    }


    public String createTransaction(Transaction transaction, Locale locale) {
        Optional<BankAccount> senderAccount = bankAccountRepository.findByBankAccountId(transaction.getSenderAccountId());
        Optional<BankAccount> receiverAccount = bankAccountRepository.findByBankAccountId(transaction.getReceiverAccountId());

        if (senderAccount.isEmpty() || receiverAccount.isEmpty()) {
            return messageSource.getMessage("sender.receiver.notfound", null, locale);
        }
        if (senderAccount.get().getBalance().compareTo(BigDecimal.valueOf(transaction.getAmount())) < 0) {
            return messageSource.getMessage("insufficient.funds", null, locale);
        }

        senderAccount.get().setBalance(senderAccount.get().getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())));
        receiverAccount.get().setBalance(receiverAccount.get().getBalance().add(BigDecimal.valueOf(transaction.getAmount())));
        bankAccountRepository.save(senderAccount.get());
        bankAccountRepository.save(receiverAccount.get());

        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        notificationService.createNotification(senderAccount.get().getUser(), transaction, transaction.getAmount() + " € " + messageSource.getMessage("to.notification", null, locale) + transaction.getReceiverAccountId());
        notificationService.createNotification(receiverAccount.get().getUser(), transaction, transaction.getAmount() + " € " + messageSource.getMessage("from.notification", null, locale) + transaction.getSenderAccountId());

        return messageSource.getMessage("created.transaction", null, locale) + transaction.getTransactionId();
    }
    public int getSenderId(String token) {
        String username = jwtUtil.extractUsername(token);
        BankAccount bankAccount = bankAccountRepository.findByUser(userRepository.findByUsername(username)).orElseThrow(() -> new RuntimeException("User not found"));
        return bankAccount.getBankAccountId();
    }

    public List<Transaction> getTransactions(String token) {
        if (token == null) {
            return List.of();
        }
        String username = jwtUtil.extractUsername(token);
        BankAccount bankAccount = bankAccountRepository.findByUser(userRepository.findByUsername(username)).orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findAllBySenderAccountIdOrReceiverAccountId(bankAccount.getBankAccountId(), bankAccount.getBankAccountId());
    }
}