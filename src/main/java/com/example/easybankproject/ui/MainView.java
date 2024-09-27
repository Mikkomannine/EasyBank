package com.example.easybankproject.ui;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.utils.JwtUtil;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@PageTitle("Main")
@Route(value = "main", layout = MainLayout.class)
public class MainView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final Paragraph balanceParagraph;

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final Grid<Transaction> transactionGrid;
    public MainView(JwtUtil jwtUtil, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.transactionGrid = new Grid<>(Transaction.class);
        this.restTemplate = new RestTemplate();
        this.balanceParagraph = new Paragraph();
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;


        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        Paragraph textLarge = new Paragraph();

        Button buttonPrimary4 = new Button("+ New Transaction", event -> openTransactionDialog());
        H2 h2 = new H2();
        Tabs tabs = new Tabs();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        balanceParagraph.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        balanceParagraph.getStyle().set("left", "100");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn2.addClassName(Gap.XLARGE);
        layoutColumn2.setWidth("250px");
        layoutColumn2.setHeight("100%");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.CENTER);

        layoutColumn3.setHeight("100%");
        textLarge.setText("Total Balance:");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        layoutColumn3.setAlignSelf(Alignment.START);

        layoutColumn3.setAlignSelf(Alignment.START, buttonPrimary4);
        buttonPrimary4.setWidth("min-content");
        buttonPrimary4.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h2.setText("Transaction History:");
        layoutColumn3.setAlignSelf(Alignment.START, h2);
        h2.setWidth("max-content");
        tabs.setWidth("100%");


        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");

        buttonPrimary4.getStyle().set("background-color", "hsl(99, 86%, 64%)");
        tabs.getStyle().set("color", "hsl(99, 86%, 64%)");

        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(textLarge);
        layoutColumn3.add(balanceParagraph);
        layoutColumn3.add(buttonPrimary4, h2, transactionGrid);
        layoutColumn3.add(tabs);
        getContent().add(layoutRow2);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        String username = (String) VaadinSession.getCurrent().getAttribute("username");

        if (token == null || !jwtUtil.validateToken(token, username)) {
            Notification.show("Please log in.");
            event.rerouteTo(LoginView.class);
        }
        fetchBalance();
        fetchTransactions();
    }

    private void fetchTransactions() {
        try {
            // Get JWT Token from session or any other storage
            String token = (String) VaadinSession.getCurrent().getAttribute("token");

            // Set up the headers for the request, including the JWT token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Transaction[]> response = restTemplate.exchange(
                    "http://localhost:8080/api/transaction/history",  // Replace with your backend endpoint
                    HttpMethod.GET,
                    entity,
                    Transaction[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                List<Transaction> transactions = Arrays.asList(response.getBody());
                // Populate the grid with transaction data
                transactionGrid.setItems(transactions);
            } else {
                Notification.show("Failed to fetch transactions: " + response.getStatusCode(), 3000, Notification.Position.MIDDLE);
            }

        } catch (HttpServerErrorException e) {
            Notification.show("Server error: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Failed to fetch transactions: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    private void fetchBalance() {
        String url = "http://localhost:8080/api/bankaccount/balance";
        String token = (String) VaadinSession.getCurrent().getAttribute("token");

        if (token == null) {
            getContent().add(new Div("Unauthorized: No token found in session."));
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            BankAccount bankAccount = restTemplate.exchange(url, HttpMethod.GET, request, BankAccount.class).getBody();
            balanceParagraph.setText("" + bankAccount.getBalance() + " €");
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

    private void openTransactionDialog() {
        Dialog dialog = new Dialog();
        VerticalLayout formLayout = new VerticalLayout();

        NumberField amountField = new NumberField("Amount");
        TextField receiverField = new TextField("Receiver Account ID");
        TextField messageField = new TextField("Message");
        H2 title = new H2("New Transaction");

        Button submitButton = new Button("Submit", event -> {
            double amount = amountField.getValue();
            int receiver = Integer.parseInt(receiverField.getValue());
            String message = messageField.getValue();
            transaction(amount, receiver, message);
            dialog.close();
        });

        formLayout.add(title, amountField, receiverField, messageField, submitButton);
        dialog.add(formLayout);
        dialog.open();
    }
   private void transaction(double amount, int receiver, String message) {

       String url = "http://localhost:8080/api/transaction/create";
       String username = (String) VaadinSession.getCurrent().getAttribute("username");
       BankAccount sender = bankAccountRepository.findByUser(userRepository.findByUsername(username)).orElseThrow(() -> new RuntimeException("User not found"));
       int senderID = sender.getBankAccountId();
       System.out.println("Sender Account: " + sender);
       System.out.println("Sender ID: " + senderID);


       String jsonPayload = String.format("{\"amount\":\"%s\",\"receiverAccountId\":\"%s\",\"message\":\"%s\",\"senderAccountId\":\"%s\"}",
               amount, receiver, message, senderID);
       System.out.println("TransactionInfo: " + jsonPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            // Make the POST request
            try {
                String transaction = restTemplate.postForObject(url, request, String.class);
                Notification.show("transaction successful: " + transaction);
            } catch (Exception e) {
                Notification.show("Error: " + e.getMessage());
            }
        }

}