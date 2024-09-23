package com.example.easybankproject.ui;



import com.example.easybankproject.models.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        // Retrieve the JWT token from the Vaadin session
        String token = (String) VaadinSession.getCurrent().getAttribute("token");

        if (token == null) {
            getContent().add(new Div("Unauthorized: No token found in session."));
            return;
        }

        // Set up headers with the JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            User user = restTemplate.exchange(url, HttpMethod.GET, request, User.class).getBody();

            if (user != null) {
                getContent().add(new Div("Username: " + user.getUsername()));
                getContent().add(new Div("Email: " + user.getEmail()));
                getContent().add(new Div("Address: " + user.getAddress()));
                getContent().add(new Div("Phone Number: " + user.getPhonenumber()));
                getContent().add(new Div("First Name: " + user.getFirstname()));
                getContent().add(new Div("Last Name: " + user.getLastname()));
            } else {
                getContent().add(new Div("Error: User not found."));
            }

        } catch (Exception e) {
            getContent().add(new Div("Error: " + e.getMessage()));
        }
    }
}
