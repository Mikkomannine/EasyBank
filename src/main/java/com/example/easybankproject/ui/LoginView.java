/*package com.example.easybankproject.ui;

import com.example.easybankproject.controllers.UserController;
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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static javax.swing.UIManager.getUI;


@Route("login")
public class LoginView extends UserController {
    private final RestTemplate restTemplate;
    public LoginView() {
        this.restTemplate = new RestTemplate();


        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        usernameField.addClassName("field");
        passwordField.addClassName("field");

        H2 h2 = new H2();
        h2.setText("Your Easy Path To Financial Freedom .");
        h2.setWidth("max-content");

        Button loginButton = new Button("Login", event -> loginUser(usernameField.getValue(), passwordField.getValue()));
        loginButton.addClassName("transaction-btn");
        RouterLink registerLink = new RouterLink("Don't have an account? Register here.", RegisterationView.class);

        Image logo = new Image("images/easybank_logo.jpg", "Company Logo");
        logo.setHeight("200px");
        logo.setWidth("200px");

        HorizontalLayout layoutRow = new HorizontalLayout();
        layoutRow.addClassName("layout-row");
        VerticalLayout layoutColumn = new VerticalLayout();
        layoutColumn.addClassName("login-body");
        VerticalLayout main= new VerticalLayout();
        main.addClassName("main");

        layoutRow.add(logo, h2);
        layoutColumn.add(usernameField, passwordField, loginButton, registerLink);

        main.add(layoutRow, layoutColumn);
        add(main);
    }

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

                VaadinSession session = VaadinSession.getCurrent();
                session.setAttribute("token", token);
                session.setAttribute("username", username);

                System.out.println("Session ID: " + session.getSession().getId());
                System.out.println("Token set in session: " + session.getAttribute("token"));
                System.out.println("Username set in session: " + session.getAttribute("username"));

                Notification.show("Login successful!");
                getUI().ifPresent(ui -> ui.navigate("main"));
            } else {
                Notification.show("Login failed. Please check your username and password.");
            }

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}*/
package com.example.easybankproject.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Route("login")
@CssImport("./styles/mainlayout.css")
public class LoginView extends VerticalLayout {
    private final RestTemplate restTemplate;
    public LoginView() {
        this.restTemplate = new RestTemplate();


        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        usernameField.addClassName("field");
        passwordField.addClassName("field");

        H2 h2 = new H2();
        h2.setText("Your Easy Path To Financial Freedom .");
        h2.setWidth("max-content");

        Button loginButton = new Button("Login", event -> loginUser(usernameField.getValue(), passwordField.getValue()));
        loginButton.addClassName("transaction-btn");
        RouterLink registerLink = new RouterLink("Don't have an account? Register here.", RegisterationView.class);

        Image logo = new Image("images/easybank_logo.jpg", "Company Logo");
        logo.setHeight("200px");
        logo.setWidth("200px");

        HorizontalLayout layoutRow = new HorizontalLayout();
        layoutRow.addClassName("layout-row");
        VerticalLayout layoutColumn = new VerticalLayout();
        layoutColumn.addClassName("login-body");
        VerticalLayout main= new VerticalLayout();
        main.addClassName("main");

        layoutRow.add(logo, h2);
        layoutColumn.add(usernameField, passwordField, loginButton, registerLink);

        main.add(layoutRow, layoutColumn);
        add(main);
    }

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

                VaadinSession session = VaadinSession.getCurrent();
                session.setAttribute("token", token);
                session.setAttribute("username", username);

                System.out.println("Session ID: " + session.getSession().getId());
                System.out.println("Token set in session: " + session.getAttribute("token"));
                System.out.println("Username set in session: " + session.getAttribute("username"));

                Notification.show("Login successful!");
                getUI().ifPresent(ui -> ui.navigate("main"));
            } else {
                Notification.show("Login failed. Please check your username and password.");
            }

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}