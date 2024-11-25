package com.example.easybankproject.repositorytests;

import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    private Transaction transaction1;



    @BeforeEach
    public void setup() {
        transaction1 = new Transaction();
        transaction1.setSenderAccountId(100);
        transaction1.setReceiverAccountId(200);
        transaction1.setAmount(1);
        transaction1.setMessage("Test transaction 1");
        transactionRepository.save(transaction1);

    }

    @Test
    void testFindAllBySenderAccountIdOrReceiverAccountId() {
        List<Transaction> transactions = transactionRepository.findAllBySenderAccountIdOrReceiverAccountId(100, 200);
        System.out.println("Transactions: " + transactions);

        assertThat(transactions)
                .isNotEmpty()
                .hasSize(1)
                .element(0)
                .extracting(Transaction::getMessage)
                .isEqualTo("Test transaction 1");
    }
}


