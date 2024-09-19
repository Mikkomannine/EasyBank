package com.example.easybankproject.ui;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
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
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


@PageTitle("Registeration")
@Route(value = "register")
@RouteAlias(value = "")
public class RegisterationView extends Composite<VerticalLayout> {
    private final RestTemplate restTemplate;
    public RegisterationView() {
        this.restTemplate = new RestTemplate();

        TextField username = new TextField("Username");
        TextField firstname = new TextField("First Name");
        TextField lastname = new TextField("Last Name");
        EmailField emailField = new EmailField("Email");
        TextField phonenumber = new TextField("Phone Number");
        TextField address = new TextField("Address");
        PasswordField passwordField = new PasswordField("Password");

        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn = new VerticalLayout();
        VerticalLayout main= new VerticalLayout();
        main.setWidth("100%");
        main.setHeight("800px");
        main.setJustifyContentMode(JustifyContentMode.START);
        main.setAlignItems(Alignment.CENTER);
        getContent().setAlignItems(Alignment.CENTER);

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
        layoutColumn.getStyle().set("border-radius", "10px");
        layoutColumn.getStyle().set("margin-bottom", "20px");


        H2 h2 = new H2();
        h2.setText("Your Easy Path To Financial Freedom .");
        h2.setWidth("max-content");

        Button registerButton = new Button("Register", event -> registerUser(username.getValue() , passwordField.getValue(), emailField.getValue(), firstname.getValue(), lastname.getValue(), phonenumber.getValue(), address.getValue()));
        RouterLink loginLink = new RouterLink("Already have an account? Login here.", LoginView.class);

        Image logo = new Image("images/easybank_logo.jpg", "Company Logo");
        logo.setHeight("200px");
        logo.setWidth("200px");

        registerButton.getStyle().set("background-color", "hsl(99, 86%, 64%)");
        registerButton.getStyle().set("color", "white");
        ;
        // Set layout size
        getContent().setSizeFull();

        layoutRow.add(logo, h2);
        layoutColumn.add(username, firstname, lastname, emailField, phonenumber, address, passwordField, registerButton, loginLink);
        layoutColumn.getStyle().set("border-radius", "10px");
        layoutColumn.getStyle().set("box-shadow", "0 0 10px hsl(99, 86%, 64%)");
        layoutColumn.setWidth("400px");

        main.add(layoutRow, layoutColumn);
        getContent().add(main);

    }
    private void registerUser(String username, String password, String email, String firstname, String lastname, String phonenumber, String address) {
        String url = "http://localhost:8080/api/user/register";

        // Create a new user JSON object
        String jsonPayload = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"firstname\":\"%s\",\"lastname\":\"%s\",\"phonenumber\":\"%s\",\"address\":\"%s\"}",
                username, password, email, firstname, lastname, phonenumber, address);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the JSON payload and headers
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request
        try {
            if (username.isEmpty() || password.isEmpty()) {
                Notification.show("Please fill in all fields");
                return;
            }
            System.out.println(jsonPayload);
            String token = restTemplate.postForObject(url, request, String.class);
            Notification.show(token);

            VaadinSession.getCurrent().setAttribute("token", token);
            VaadinSession.getCurrent().setAttribute("username", username);

            getUI().ifPresent(ui -> ui.navigate("main"));

        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}