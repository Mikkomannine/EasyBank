
package com.example.easybankproject.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@CssImport("./styles/mainlayout.css")
@PreserveOnRefresh
public class MainLayout extends AppLayout {

    private int notificationsCount = 0;
    private Button notificationsLink = new Button("Notis: " + notificationsCount);
    private Grid<com.example.easybankproject.models.Notification> grid;
    private List<com.example.easybankproject.models.Notification> notificationList;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addNavBarContent();
        addDrawerContent();
        fetchNotificationsCount();
    }

    private void addNavBarContent() {
        var toggle = new DrawerToggle();
        toggle.setAriaLabel("Toggle menu");
        toggle.setTooltipText("Toggle menu");
        toggle.addClassName("toggle");
        var header = new Header(toggle);
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.BETWEEN, LumoUtility.Padding.End.MEDIUM);

        addToNavbar(false, header);
    }


    private void addDrawerContent() {
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setWidthFull();
        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        addToDrawer(drawerLayout);

        Image logo = new Image("./images/easybank_logo-1__1_-removebg-preview.png", "logo");
        logo.setWidth("180x");
        logo.setHeight("200px");
        drawerLayout.add(logo);

        VerticalLayout profileLayout = new VerticalLayout();
        profileLayout.setWidth("40px");
        profileLayout.setHeight("80px");
        profileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink profileLink = new RouterLink("MyProfile", ProfileView.class);
        profileLink.addClassNames("btn-hover", "color-5");
        profileLayout.add(profileLink);

        VerticalLayout HomeLayout = new VerticalLayout();
        HomeLayout.setWidth("40px");
        HomeLayout.setHeight("80px");
        HomeLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        RouterLink homeLink = new RouterLink("Home", MainView.class);
        homeLink.addClassNames("btn-hover", "color-5");
        HomeLayout.add(homeLink);

        VerticalLayout LogoutLayout = new VerticalLayout();
        LogoutLayout.setWidth("40px");
        LogoutLayout.setHeight("80px");
        LogoutLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Button logoutButton = new Button("Logout", event -> {
            VaadinSession.getCurrent().setAttribute("token", null);
            VaadinSession.getCurrent().getSession().invalidate();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        logoutButton.addClassNames("logout-btn-hover", "color-5");
        LogoutLayout.add(logoutButton);



        drawerLayout.add(profileLayout, HomeLayout, LogoutLayout);
    }

    public void fetchNotificationsCount() {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        if (token == null) {
            Notification.show("Unauthorized: No token found in session.");
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/notifications/count";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Integer> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Integer.class
            );
            notificationsCount = response.getBody();
            notificationsLink.setText("Notis: " + notificationsCount);
            System.out.println("Notifications count: " + notificationsCount);
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

    private void openNotificationsDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("800px");
        VerticalLayout dialogLayout = new VerticalLayout();
        dialog.add(dialogLayout);

        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        if (token == null) {
            Notification.show("Unauthorized: No token found in session.");
            return;
        }

        notificationList = fetchNotifications(token);
        if (notificationList == null) {
            return;
        }

        grid = new Grid<>(com.example.easybankproject.models.Notification.class);
        grid.setItems(notificationList);
        grid.setColumns("content", "timestamp");

        grid.addComponentColumn(notification -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(event -> {
                deleteNotification(notification.getNotificationId(), token);
            });
            return deleteButton;
        }).setHeader("Actions");

        dialogLayout.add(grid);
        dialog.open();
    }

    private List<com.example.easybankproject.models.Notification> fetchNotifications(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/notifications";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<com.example.easybankproject.models.Notification[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    com.example.easybankproject.models.Notification[].class
            );
            List<com.example.easybankproject.models.Notification> notificationList = Arrays.asList(response.getBody());
            notificationList.sort((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()));
            return notificationList;
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
            return null;
        }
    }

    private void deleteNotification(Integer notificationId, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String deleteUrl = "http://localhost:8080/api/notifications/" + notificationId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> deleteEntity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(deleteUrl, HttpMethod.DELETE, deleteEntity, Void.class);
            Notification.show("Notification deleted");
            // Refresh the notification count and list
            fetchNotificationsCount();
            notificationList = fetchNotifications(token);
            grid.setItems(notificationList);
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        fetchNotificationsCount();
    }
}