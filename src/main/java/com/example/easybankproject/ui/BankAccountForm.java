package com.example.easybankproject.ui;


import com.example.easybankproject.models.BankAccount;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Route("create-account")
public class BankAccountForm extends FormLayout {

    private TextField accountNumberField = new TextField("Account Number");

    private Button submitButton = new Button("Create Account");

    private final RestTemplate restTemplate;

    @Autowired
    public BankAccountForm(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        submitButton.addClickListener(e -> createAccount());

        add(accountNumberField, submitButton);
    }

    private void createAccount() {
        String accountNumber = accountNumberField.getValue();
        Long userId = 1L; // Fetch this dynamically depending on logged-in user

        try {
            String url = String.format("http://localhost:8080/api/accounts/create?userId=%d&accountNumber=%s", userId, accountNumber);
            BankAccount response = restTemplate.postForObject(url, null, BankAccount.class);

            if (response != null) {
                Notification.show("Bank Account Created Successfully");
            } else {
                Notification.show("Error: Account creation failed");
            }
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}
