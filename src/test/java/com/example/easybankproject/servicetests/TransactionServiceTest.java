package com.example.easybankproject.servicetests;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.models.User;
import com.example.easybankproject.services.NotificationService;
import com.example.easybankproject.services.TransactionService;
import com.example.easybankproject.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private BankAccount senderAccount;
    private BankAccount receiverAccount;
    private Locale locale;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setSenderAccountId(1);
        transaction.setReceiverAccountId(2);
        transaction.setAmount(100.0);
        transaction.setTimestamp(LocalDateTime.now());

        senderAccount = new BankAccount();
        senderAccount.setBankAccountId(1);
        senderAccount.setBalance(BigDecimal.valueOf(200.0));

        receiverAccount = new BankAccount();
        receiverAccount.setBankAccountId(2);
        receiverAccount.setBalance(BigDecimal.valueOf(100.0));

        locale = Locale.ENGLISH;
    }

    @Test
    public void testCreateTransaction_Success() {
        when(bankAccountRepository.findByBankAccountId(1)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByBankAccountId(2)).thenReturn(Optional.of(receiverAccount));
        when(messageSource.getMessage("created.transaction", null, locale)).thenReturn("Transaction created successfully. ID: ");
        when(messageSource.getMessage("to.notification", null, locale)).thenReturn("to");
        when(messageSource.getMessage("from.notification", null, locale)).thenReturn("from");

        String result = transactionService.createTransaction(transaction, locale);

        assertTrue(result.contains("Transaction created successfully. ID: "));
        verify(bankAccountRepository).save(senderAccount);
        verify(bankAccountRepository).save(receiverAccount);
        verify(transactionRepository).save(transaction);
        verify(notificationService, times(2)).createNotification(any(), any(), anyString());
    }

    @Test
    public void testCreateTransaction_InsufficientFunds() {
        senderAccount.setBalance(BigDecimal.valueOf(50.0));
        when(bankAccountRepository.findByBankAccountId(1)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByBankAccountId(2)).thenReturn(Optional.of(receiverAccount));
        when(messageSource.getMessage("insufficient.funds", null, locale)).thenReturn("Insufficient funds.");

        String result = transactionService.createTransaction(transaction, locale);

        assertEquals("Insufficient funds.", result);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(notificationService, never()).createNotification(any(), any(), anyString());
    }

    @Test
    public void testCreateTransaction_AccountNotFound() {
        when(bankAccountRepository.findByBankAccountId(1)).thenReturn(Optional.empty());
        when(messageSource.getMessage("sender.receiver.notfound", null, locale)).thenReturn("Sender or receiver account not found.");

        String result = transactionService.createTransaction(transaction, locale);

        assertEquals("Sender or receiver account not found.", result);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(notificationService, never()).createNotification(any(), any(), anyString());
    }

    @Test
    public void testGetSenderId_Success() {
        when(jwtUtil.extractUsername(anyString())).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(new User());
        when(bankAccountRepository.findByUser(any(User.class))).thenReturn(Optional.of(senderAccount));

        int senderId = transactionService.getSenderId("token");

        assertEquals(1, senderId);
    }

    @Test
    public void testGetTransactions_Success() {
        when(jwtUtil.extractUsername(anyString())).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(new User());
        when(bankAccountRepository.findByUser(any(User.class))).thenReturn(Optional.of(senderAccount));
        when(transactionRepository.findAllBySenderAccountIdOrReceiverAccountId(1, 1)).thenReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.getTransactions("token");

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }

    @Test
    public void testGetTransactions_TokenNull() {
        List<Transaction> transactions = transactionService.getTransactions(null);

        assertNull(transactions);
    }
}