package com.example.easybankproject.ui;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@PageTitle("Registeration")
@Route(value = "")
@RouteAlias(value = "")
public class RegisterationView extends Composite<VerticalLayout> {
    private final RestTemplate restTemplate;
    public RegisterationView() {
        this.restTemplate = new RestTemplate();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Image image = new Image("easybank_logo.jpg", "Description of image"); // Replace with your image path
        H2 h2 = new H2();
        TextField firstname = new TextField();
        TextField textField2 = new TextField();
        EmailField emailField = new EmailField();
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button("Register", event -> registerUser(firstname.getValue(), passwordField.getValue(), emailField.getValue()));
        getContent().setWidth("100%");
        getContent().setHeight("800px");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("700px");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.XLARGE);
        layoutRow.addClassName(Padding.XLARGE);
        layoutRow.setWidth("1100px");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        image.setWidth("50px"); // Set the desired width
        image.setHeight("50px"); // Set the desired height
        h2.setText("Your Easy Path To Financial Freedom .");
        h2.setWidth("max-content");
        firstname.setLabel("Firstname");
        firstname.setWidth("min-content");
        textField2.setLabel("Lastname");
        textField2.setWidth("min-content");
        emailField.setLabel("Email");
        emailField.setWidth("min-content");
        passwordField.setLabel("Password field");
        passwordField.setWidth("min-content");
        registerButton.setText("Register");
        registerButton.setWidth("min-content");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(image);
        layoutRow.add(h2);
        layoutColumn2.add(firstname);
        layoutColumn2.add(textField2);
        layoutColumn2.add(emailField);
        layoutColumn2.add(passwordField);
        layoutColumn2.add(registerButton);
    }
    private void registerUser(String username, String password, String email) {
        String url = "http://localhost:8080/api/user/register";

        // Create a new user JSON object
        String jsonPayload = String.format("{\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
                username, email, password);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the JSON payload and headers
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request
        try {
            String token = restTemplate.postForObject(url, request, String.class);
            Notification.show(token);
            // Store the token in Vaadin session
            VaadinSession.getCurrent().setAttribute("jwtToken", token);

            // Navigate to MainView
            getUI().ifPresent(ui -> ui.navigate("main"));

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}