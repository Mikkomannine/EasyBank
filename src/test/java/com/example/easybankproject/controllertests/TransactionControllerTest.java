package com.example.easybankproject.controllertests;


import com.example.easybankproject.controllers.TransactionController;
import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.NotificationRepository;
import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Notification;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private TransactionController transactionController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user;
    private BankAccount senderAccount;
    private BankAccount receiverAccount;
    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        user = new User();
        user.setUsername("testuser");

        senderAccount = new BankAccount();
        senderAccount.setBankAccountId(1);
        senderAccount.setUser(user);
        senderAccount.setBalance(BigDecimal.valueOf(1000));

        receiverAccount = new BankAccount();
        receiverAccount.setBankAccountId(2);
        receiverAccount.setUser(user);
        receiverAccount.setBalance(BigDecimal.valueOf(500));

        transaction = new Transaction();
        transaction.setSenderAccountId(1);
        transaction.setReceiverAccountId(2);
        transaction.setAmount(100);
        transaction.setMessage("Test transaction");
    }

    @Test
    public void testCreateTransactionSuccess() throws Exception {
        // Arrange
        when(bankAccountRepository.findByBankAccountId(1)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByBankAccountId(2)).thenReturn(Optional.of(receiverAccount));

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/api/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction created with ID: " + transaction.getTransactionId()));
    }

    @Test
    public void testCreateTransactionSenderOrReceiverNotFound() throws Exception {
        // Arrange
        when(bankAccountRepository.findByBankAccountId(1)).thenReturn(Optional.empty());

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/api/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Sender or receiver account not found."));
    }

    @Test
    public void testCreateTransactionInsufficientFunds() throws Exception {
        // Arrange
        transaction.setAmount(2000); // Set amount greater than sender's balance
        when(bankAccountRepository.findByBankAccountId(1)).thenReturn(Optional.of(senderAccount));
        when(bankAccountRepository.findByBankAccountId(2)).thenReturn(Optional.of(receiverAccount));

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/api/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds."));
    }

    @Test
    public void testGetTransactionsSuccess() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(jwtUtil.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(bankAccountRepository.findByUser(user)).thenReturn(Optional.of(senderAccount));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        when(transactionRepository.findAllBySenderAccountIdOrReceiverAccountId(1, 1)).thenReturn(transactions);

        // Act & Assert
        mockMvc.perform(get("/api/transaction/history")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Test transaction"));
    }

}