package com.example.easybankproject.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Route("login")
//@CssImport("./login-view.css") // Import the CSS file
public class LoginView extends VerticalLayout {

    private final RestTemplate restTemplate;

    public LoginView() {
        this.restTemplate = new RestTemplate();
        TextField usernameField = new TextField("UserID");
        PasswordField passwordField = new PasswordField("Password");

        Button loginButton = new Button("Login", event -> loginUser(usernameField.getValue(), passwordField.getValue()));
        Image logo = new Image("images/img.png", "Company Logo");

        // Apply CSS class
        addClassName("login-view-container");
        logo.addClassName("logo");

        // Set layout size
        setSizeFull();

        add(usernameField, passwordField, loginButton, logo);
    }

    private void loginUser(String username, String password) {
        String url = "http://localhost:8080/api/user/login";

        // Create a new login JSON object
        String jsonPayload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the JSON payload and headers
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request
        try {
            String token = restTemplate.postForObject(url, request, String.class);
            if (token != null && !token.isEmpty()) {
                Notification.show("Login successful!");
                // Store the token in Vaadin session
                VaadinSession.getCurrent().setAttribute("jwtToken", token);

                // Navigate to the MainView or Dashboard
                getUI().ifPresent(ui -> ui.navigate("main"));
            } else {
                Notification.show("Login failed. Please check your username and password.");
            }

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}
