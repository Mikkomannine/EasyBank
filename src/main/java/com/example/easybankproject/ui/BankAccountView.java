package com.example.easybankproject.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Route("bankaccount")
public class BankAccountView extends VerticalLayout {

    private RestTemplate restTemplate = new RestTemplate();

    public BankAccountView() {
        TextField balanceField = new TextField("Initial Balance");
        Button createButton = new Button("Create Bank Account");

        createButton.addClickListener(event -> {
            String balance = balanceField.getValue();
            createBankAccount(balance);
        });

        add(balanceField, createButton);
    }

    private void createBankAccount(String balance) {
        String url = "http://localhost:8080/api/bankaccount/create?initialBalance=" + balance;

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with headers
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            // Make the POST request
            String response = restTemplate.postForObject(url, request, String.class);
            Notification.show(response);  // Show the response in a notification
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}
