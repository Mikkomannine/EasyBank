package com.example.easybankproject.ui;

import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.utils.JwtUtil;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


@PageTitle("Main")
@Route(value = "main", layout = MainLayout.class)
public class MainView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final RestTemplate restTemplate;

    private final JwtUtil jwtUtil;

    private final Paragraph balanceParagraph;


    public MainView(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.restTemplate = new RestTemplate();
        this.balanceParagraph = new Paragraph();
        fetchBalance();

        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        Paragraph textLarge = new Paragraph();

        Button buttonPrimary4 = new Button("+ New Payment", event -> paymentButton());
        H2 h2 = new H2();
        Tabs tabs = new Tabs();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button createaccount = new Button("Create Account", event -> createButton());
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
        h2.setText("My Accounts:");
        layoutColumn3.setAlignSelf(Alignment.START, h2);
        h2.setWidth("max-content");
        tabs.setWidth("100%");


        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");

        buttonPrimary4.getStyle().set("background-color", "hsl(99, 86%, 64%)");
        createaccount.getStyle().set("color", "hsl(99, 86%, 64%)");
        tabs.getStyle().set("color", "hsl(99, 86%, 64%)");


        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(textLarge);
        layoutColumn3.add(balanceParagraph);
        layoutColumn3.add(buttonPrimary4);
        layoutColumn3.add(createaccount);
        layoutColumn3.add(h2);
        layoutColumn3.add(tabs);
        getContent().add(layoutRow2);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Check if token exists in Vaadin session
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        String username = (String) VaadinSession.getCurrent().getAttribute("username");

        if (token == null || !jwtUtil.validateToken(token, username)) {
            Notification.show("Please log in.");
            event.rerouteTo(LoginView.class);
        }

    }

    private void fetchBalance() {
        String url = "http://localhost:8080/api/bankaccount/balance";
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
            BankAccount bankAccount = restTemplate.exchange(url, HttpMethod.GET, request, BankAccount.class).getBody();
            balanceParagraph.setText("" + bankAccount.getBalance());
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

private void createButton() {
    getUI().ifPresent(ui -> ui.navigate("create-bank-account"));
}
private void paymentButton() {
        getUI().ifPresent(ui -> ui.navigate("payment"));
    }
}
//f821bd5197bc45ca831021e8753eac5b