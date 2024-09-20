// src/main/java/com/example/easybankproject/ui/BankAccountView.java
package com.example.easybankproject.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@PageTitle("Create Bank Account")
@Route(value = "create-bank-account")
public class BankAccountView extends Composite<VerticalLayout> {
    private final RestTemplate restTemplate;

    public BankAccountView() {
        this.restTemplate = new RestTemplate();

        BigDecimalField initialBalanceField = new BigDecimalField("Initial Balance");

        Button createAccountButton = new Button("Create Account", event -> createBankAccount(initialBalanceField.getValue()));

        getContent().add(initialBalanceField, createAccountButton);
    }

    private void createBankAccount(BigDecimal initialBalance) {
        String url = "http://localhost:8080/api/bankaccount/create";

        // Create a new bank account JSON object
        String jsonPayload = String.format("{\"balance\":%s}", initialBalance);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the JSON payload and headers
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request
        try {
            if (initialBalance == null) {
                Notification.show("Please fill in all fields");
                return;
            }
            String response = restTemplate.postForObject(url, request, String.class);
            Notification.show(response);

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}