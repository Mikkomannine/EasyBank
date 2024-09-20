package com.example.easybankproject.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@Route("login")
//@CssImport("./login-view.css") // Import the CSS file
public class LoginView extends VerticalLayout {

    private final RestTemplate restTemplate;

    public LoginView() {
        this.restTemplate = new RestTemplate();


        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn = new VerticalLayout();

        VerticalLayout main= new VerticalLayout();

        layoutRow.setWidthFull();
        layoutRow.addClassName(LumoUtility.Gap.XLARGE);
        layoutRow.addClassName(LumoUtility.Padding.XLARGE);
        layoutRow.setWidth("1100px");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);

        layoutColumn.setWidthFull();
        layoutColumn.setFlexGrow(1.0, layoutRow);
        layoutColumn.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn.setAlignItems(Alignment.CENTER);

        main.setWidth("100%");
        main.setHeight("800px");
        main.setJustifyContentMode(JustifyContentMode.START);
        main.setAlignItems(Alignment.CENTER);

        H2 h2 = new H2();
        h2.setText("Your Easy Path To Financial Freedom .");
        h2.setWidth("max-content");

        Button loginButton = new Button("Login", event -> loginUser(usernameField.getValue(), passwordField.getValue()));
        RouterLink registerLink = new RouterLink("Don't have an account? Register here.", RegisterationView.class);

        Image logo = new Image("images/easybank_logo.jpg", "Company Logo");
        logo.setHeight("200px");
        logo.setWidth("200px");

        // Apply CSS class
        //addClassName("login-view-container");
        //logo.addClassName("logo");
        loginButton.getStyle().set("background-color", "hsl(99, 86%, 64%)");
        loginButton.getStyle().set("color", "white");
        ;
        // Set layout size
        setSizeFull();

        layoutRow.add(logo, h2);
        layoutColumn.add(usernameField, passwordField, loginButton, registerLink);
        layoutColumn.getStyle().set("border-radius", "10px");
        layoutColumn.getStyle().set("box-shadow", "0 0 10px hsl(99, 86%, 64%)");
        layoutColumn.setWidth("400px");

        main.add(layoutRow, layoutColumn);
        add(main);


    }

    // src/main/java/com/example/easybankproject/ui/LoginView.java
    private void loginUser(String username, String password) {
        String url = "http://localhost:8080/api/user/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonPayload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Response: " + response);
            if (response.getStatusCode() == HttpStatus.OK) {
                String token = response.getBody();

                // Store the JWT token in Vaadin session
                VaadinSession session = VaadinSession.getCurrent();
                session.setAttribute("token", token);
                session.setAttribute("username", username);

                // Log the session ID and attributes for debugging
                System.out.println("Session ID: " + session.getSession().getId());
                System.out.println("Token set in session: " + session.getAttribute("token"));
                System.out.println("Username set in session: " + session.getAttribute("username"));

                Notification.show("Login successful!");
                getUI().ifPresent(ui -> ui.navigate("main")); // Navigate to dashboard after login
            } else {
                Notification.show("Login failed. Please check your username and password.");
            }

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

/*
    private void loginUser(String username, String password) {
        String url = "http://localhost:8080/api/user/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonPayload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Response: " + response);
            if (response.getStatusCode() == HttpStatus.OK) {
                String token = response.getBody();

                // Store the JWT token in Vaadin session
                VaadinSession.getCurrent().setAttribute("token", token);
                VaadinSession.getCurrent().setAttribute("username", username);
                System.out.println("Token set in session: " + VaadinSession.getCurrent().getAttribute("token"));
                System.out.println("Username set in session: " + username);
                Notification.show("Login successful!");
                getUI().ifPresent(ui -> ui.navigate("main")); // Navigate to dashboard after login
            } else {
                Notification.show("Login failed. Please check your username and password.");
            }

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }*/
}