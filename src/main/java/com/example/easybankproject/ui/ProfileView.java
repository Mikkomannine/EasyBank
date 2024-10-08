package com.example.easybankproject.ui;



import com.example.easybankproject.models.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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
            TextField emailField = new TextField("Email", user.getEmail(), "");
            TextField addressField = new TextField("Address", user.getAddress(), "");
            TextField phoneField = new TextField("Phone Number", String.valueOf(user.getPhonenumber()), "");
            TextField firstNameField = new TextField("First Name", user.getFirstname(), "");
            TextField lastNameField = new TextField("Last Name", user.getLastname(), "");

            Button updateButton = new Button("Update", event -> {
                user.setEmail(emailField.getValue());
                user.setAddress(addressField.getValue());
                user.setPhonenumber(Integer.parseInt(phoneField.getValue()));
                user.setFirstname(firstNameField.getValue());
                user.setLastname(lastNameField.getValue());

                updateUserProfile(user, token);
            });

            getContent().add(emailField, addressField, phoneField, firstNameField, lastNameField, updateButton);
        } else {
            getContent().add(new Div("Error: User not found."));
        }

    } catch (Exception e) {
        getContent().add(new Div("Error: " + e.getMessage()));
    }
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

}
