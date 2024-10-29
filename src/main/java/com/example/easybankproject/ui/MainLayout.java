/*
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
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
    }
}
*/
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
import org.springframework.context.MessageSource;
import java.util.Locale;

@CssImport("./styles/mainlayout.css")
@PreserveOnRefresh
public class MainLayout extends AppLayout {

    private final MessageSource messageSource;

    public MainLayout(MessageSource messageSource) {
        this.messageSource = messageSource;
        setPrimarySection(Section.DRAWER);
        addNavBarContent();
        addDrawerContent();
    }

    private void addNavBarContent() {
        var toggle = new DrawerToggle();
        toggle.setAriaLabel(messageSource.getMessage("toggle.menu", null, getLocale()));
        toggle.setTooltipText(messageSource.getMessage("toggle.menu", null, getLocale()));
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
        logo.setWidth("180px");
        logo.setHeight("200px");
        drawerLayout.add(logo);

        VerticalLayout profileLayout = new VerticalLayout();
        profileLayout.setWidth("40px");
        profileLayout.setHeight("80px");
        profileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink profileLink = new RouterLink(messageSource.getMessage("my.profile", null, getLocale()), ProfileView.class);
        profileLink.addClassNames("btn-hover", "color-5");
        profileLayout.add(profileLink);

        VerticalLayout homeLayout = new VerticalLayout();
        homeLayout.setWidth("40px");
        homeLayout.setHeight("80px");
        homeLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        RouterLink homeLink = new RouterLink(messageSource.getMessage("home", null, getLocale()), MainView.class);
        homeLink.addClassNames("btn-hover", "color-5");
        homeLayout.add(homeLink);

        VerticalLayout logoutLayout = new VerticalLayout();
        logoutLayout.setWidth("40px");
        logoutLayout.setHeight("80px");
        logoutLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Button logoutButton = new Button(messageSource.getMessage("logout", null, getLocale()), event -> {
            VaadinSession.getCurrent().setAttribute("token", null);
            VaadinSession.getCurrent().getSession().invalidate();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
        logoutButton.addClassNames("logout-btn-hover", "color-5");
        logoutLayout.add(logoutButton);

        drawerLayout.add(profileLayout, homeLayout, logoutLayout);
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
    }
}