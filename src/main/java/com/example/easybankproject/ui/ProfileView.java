/*
package com.example.easybankproject.ui;

import com.example.easybankproject.models.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@PageTitle("Profile")
@CssImport("./styles/profileview.css")
@Route(value = "profile", layout = MainLayout.class)
public class ProfileView extends Composite<VerticalLayout> {
    private final RestTemplate restTemplate;

    public ProfileView() {
        this.restTemplate = new RestTemplate();
        getContent().addClassName("centered-column");
        displayUserProfile();
    }

    private void displayUserProfile() {
        String url = "http://localhost:8080/api/user/me";
        String token = (String) VaadinSession.getCurrent().getAttribute("token");

        if (token == null) {
            getContent().add(new Div("Unauthorized: No token found in session."));
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            User user = restTemplate.exchange(url, HttpMethod.GET, request, User.class).getBody();

            if (user != null) {
                Div emailDiv = new Div();
                emailDiv.setText("Email: " + user.getEmail());

                Div addressDiv = new Div();
                addressDiv.setText("Address: " + user.getAddress());

                Div phoneDiv = new Div();
                phoneDiv.setText("Phone Number: " + user.getPhonenumber());

                Div firstNameDiv = new Div();
                firstNameDiv.setText("First Name: " + user.getFirstname());

                Div lastNameDiv = new Div();
                lastNameDiv.setText("Last Name: " + user.getLastname());

                Button updateButton = new Button("Update", event -> openUpdateDialog(user, token));

                getContent().add(emailDiv, addressDiv, phoneDiv, firstNameDiv, lastNameDiv, updateButton);
            } else {
                getContent().add(new Div("Error: User not found."));
            }

        } catch (Exception e) {
            getContent().add(new Div("Error: " + e.getMessage()));
        }
    }

    private void openUpdateDialog(User user, String token) {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        TextField emailField = new TextField("Email", user.getEmail(), "");
        TextField addressField = new TextField("Address", user.getAddress(), "");
        TextField phoneField = new TextField("Phone Number", String.valueOf(user.getPhonenumber()), "");
        TextField firstNameField = new TextField("First Name", user.getFirstname(), "");
        TextField lastNameField = new TextField("Last Name", user.getLastname(), "");

        Button saveButton = new Button("Save", event -> {
            user.setEmail(emailField.getValue());
            user.setAddress(addressField.getValue());
            user.setPhonenumber(Integer.parseInt(phoneField.getValue()));
            user.setFirstname(firstNameField.getValue());
            user.setLastname(lastNameField.getValue());

            updateUserProfile(user, token);
            dialog.close();
        });

        Button cancelButton = new Button("Cancel", event -> dialog.close());

        VerticalLayout dialogLayout = new VerticalLayout(emailField, addressField, phoneField, firstNameField, lastNameField, saveButton, cancelButton);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private void updateUserProfile(User user, String token) {
        String url = "http://localhost:8080/api/user/update/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Content-Type", "application/json");
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, request, User.class);
            Notification.show("Profile updated successfully");
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}*/

package com.example.easybankproject.ui;

import com.example.easybankproject.models.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@PageTitle("Profile")
@CssImport("./styles/profileview.css")
@Route(value = "profile", layout = MainLayout.class)
public class ProfileView extends Composite<VerticalLayout> {
    private final RestTemplate restTemplate;

    public ProfileView() {
        this.restTemplate = new RestTemplate();
        getContent().addClassName("centered-column");
        displayUserProfile();
    }

    private void displayUserProfile() {
        String url = "http://localhost:8080/api/user/me";
        String token = (String) VaadinSession.getCurrent().getAttribute("token");

        if (token == null) {
            getContent().add(new Div("Unauthorized: No token found in session."));
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            User user = restTemplate.exchange(url, HttpMethod.GET, request, User.class).getBody();

            if (user != null) {
                getContent().removeAll(); // Clear previous content

                VerticalLayout content = new VerticalLayout();
                content.addClassName("profile-column");

                H1 title = new H1("Personal Information:");
                H3 emailLabel = new H3("Email:");
                H3 addressLabel = new H3("Address:");
                H3 phoneLabel = new H3("Phone Number:");
                H3 firstNameLabel = new H3("First Name:");
                H3 lastNameLabel = new H3("Last Name:");

                Div emailDiv = new Div();
                emailDiv.setText(user.getEmail());

                Div addressDiv = new Div();
                addressDiv.setText(user.getAddress());

                Div phoneDiv = new Div();
                phoneDiv.setText("" + user.getPhonenumber());

                Div firstNameDiv = new Div();
                firstNameDiv.setText(user.getFirstname());

                Div lastNameDiv = new Div();
                lastNameDiv.setText(user.getLastname());

                Button updateButton = new Button("Update", event -> openUpdateDialog(user, token));
                updateButton.addClassName("transaction-btn");

                content.add(emailLabel, emailDiv, addressLabel, addressDiv, phoneLabel, phoneDiv, firstNameLabel, firstNameDiv, lastNameLabel, lastNameDiv, updateButton);
                getContent().add(title, content);
            } else {
                getContent().add(new Div("Error: User not found."));
            }

        } catch (Exception e) {
            getContent().add(new Div("Error: " + e.getMessage()));
        }
    }

    private void openUpdateDialog(User user, String token) {
        Dialog dialog = new Dialog();
        dialog.addClassName("dialog");

        H3 title = new H3("Update Profile");
        TextField emailField = new TextField("Email", user.getEmail(), "");
        TextField addressField = new TextField("Address", user.getAddress(), "");
        TextField phoneField = new TextField("Phone Number", String.valueOf(user.getPhonenumber()), "");
        TextField firstNameField = new TextField("First Name", user.getFirstname(), "");
        TextField lastNameField = new TextField("Last Name", user.getLastname(), "");
        emailField.addClassName("field");
        addressField.addClassName("field");
        phoneField.addClassName("field");
        firstNameField.addClassName("field");
        lastNameField.addClassName("field");

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button saveButton = new Button("Save", event -> {
            user.setEmail(emailField.getValue());
            user.setAddress(addressField.getValue());
            user.setPhonenumber(Integer.parseInt(phoneField.getValue()));
            user.setFirstname(firstNameField.getValue());
            user.setLastname(lastNameField.getValue());

            updateUserProfile(user, token);
            dialog.close();
        });
        saveButton.addClassName("transaction-btn");

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addClassName("delete");

        buttonsLayout.add(saveButton, cancelButton);
        VerticalLayout dialogLayout = new VerticalLayout(title, emailField, addressField, phoneField, firstNameField, lastNameField, buttonsLayout);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private void updateUserProfile(User user, String token) {
        String url = "http://localhost:8080/api/user/update/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Content-Type", "application/json");
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, request, User.class);
            Notification.show("Profile updated successfully");
            displayUserProfile(); // Refresh user information
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}