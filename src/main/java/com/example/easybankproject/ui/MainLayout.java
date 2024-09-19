package com.example.easybankproject.ui;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

@CssImport("./styles/mainlayout.css")
@PreserveOnRefresh
public class MainLayout extends AppLayout {

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addNavBarContent();
        addDrawerContent();
    }

    private void addNavBarContent() {
        var toggle = new DrawerToggle();
        toggle.setAriaLabel("Toggle menu");
        toggle.setTooltipText("Toggle menu");
        var header = new Header(toggle);
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.BETWEEN, LumoUtility.Padding.End.MEDIUM);

        addToNavbar(false, header);
    }

    private void addDrawerContent() {
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setWidthFull();
        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        addToDrawer(drawerLayout);

        Image logo = new Image("./images/easybank_logo.jpg", "logo");
        logo.setWidth("180x");
        logo.setHeight("200px");
        drawerLayout.add(logo);

        VerticalLayout profileLayout = new VerticalLayout();
        profileLayout.setWidth("40px");
        profileLayout.setHeight("80px");
        profileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink profileLink = new RouterLink("main", MainView.class);
        profileLink.addClassNames("btn-hover", "color-5");
        profileLayout.add(profileLink);

        VerticalLayout notificationsLayout = new VerticalLayout();
        notificationsLayout.setWidth("40px");
        notificationsLayout.setHeight("80px");
        notificationsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink notificationsLink = new RouterLink("main", MainView.class);
        notificationsLink.addClassNames("btn-hover", "color-5");
        notificationsLayout.add(notificationsLink);

        VerticalLayout historyLayout = new VerticalLayout();
        historyLayout.setWidth("40px");
        historyLayout.setHeight("80px");
        historyLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink historyLink = new RouterLink("main", MainView.class);
        historyLink.addClassNames("btn-hover", "color-5");
        historyLayout.add(historyLink);

        Button logoutButton = new Button("Logout", event -> {
            VaadinSession.getCurrent().setAttribute("token", null);
            // clear the session
            VaadinSession.getCurrent().getSession().invalidate();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });


        drawerLayout.add(profileLayout, notificationsLayout, historyLayout, logoutButton);

    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
    }
}