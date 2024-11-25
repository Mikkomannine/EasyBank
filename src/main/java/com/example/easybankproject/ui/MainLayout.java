package com.example.easybankproject.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@CssImport("./styles/mainlayout.css")
@PreserveOnRefresh
public class MainLayout extends AppLayout {

    private transient MessageSource messageSource;
    private Button logoutButton;
    private RouterLink profileLink;
    private RouterLink homeLink;

    public MainLayout(MessageSource messageSource) {
        this.messageSource = messageSource;
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
        logo.setWidth("180px");
        logo.setHeight("200px");
        drawerLayout.add(logo);

        profileLink = createDrawerLink("profile.page", ProfileView.class);
        homeLink = createDrawerLink("home.page", MainView.class);
        logoutButton = createLogoutButton();

        drawerLayout.add(profileLink);
        drawerLayout.add(homeLink);
        drawerLayout.add(logoutButton);

        VerticalLayout languageLayout = createLanguageFlags();
        languageLayout.addClassName("language-layout");
        drawerLayout.add(languageLayout);
    }

    private RouterLink createDrawerLink(String textKey, Class<?> navigationTarget) {
        RouterLink link = new RouterLink(getMessage(textKey), (Class<? extends Component>) navigationTarget);
        link.addClassNames("btn-hover", "color-5");
        return link;
    }



    private Button createLogoutButton() {
        Button button = new Button(getMessage("logout.button"), event -> {
            // Navigate to the login view
            getUI().ifPresent(ui -> ui.navigate("login"));
            VaadinSession.getCurrent().setAttribute("token", null);
        });
        button.addClassNames("logout-btn-hover", "color-5");
        return button;
    }




    private VerticalLayout createLanguageFlags() {
        Image englishFlag = new Image("images/united-kingdom.png", getMessage("English"));
        englishFlag.addClickListener(event -> changeLanguage(Locale.forLanguageTag("en")));

        Image finnishFlag = new Image("images/finland.png", getMessage("Finland"));
        finnishFlag.addClickListener(event -> changeLanguage(Locale.forLanguageTag("fi")));

        Image arabicFlag = new Image("images/arabic.png", getMessage("Arabic"));
        arabicFlag.addClickListener(event -> changeLanguage(Locale.forLanguageTag("ar")));

        Image koreanFlag = new Image("images/south-korea.png", getMessage("Korean"));
        koreanFlag.addClickListener(event -> changeLanguage(Locale.forLanguageTag("ko")));

        Image spanishFlag = new Image("images/spanish.png", getMessage("Spanish"));
        spanishFlag.addClickListener(event -> changeLanguage(Locale.forLanguageTag("es")));

        Image japaneseFlag = new Image("images/japan.png", getMessage("Japanese"));
        japaneseFlag.addClickListener(event -> changeLanguage(Locale.forLanguageTag("ja")));

        // Set dimensions for all flags in a loop
        List<Image> flags = Arrays.asList(englishFlag, finnishFlag, arabicFlag, koreanFlag, spanishFlag, japaneseFlag);
        for (Image flag : flags) {
            flag.setHeight("30px");
            flag.setWidth("30px");
        }

        HorizontalLayout languageLayout1 = new HorizontalLayout(englishFlag, finnishFlag, arabicFlag);
        HorizontalLayout languageLayout2 = new HorizontalLayout(koreanFlag, spanishFlag, japaneseFlag);
        return new VerticalLayout(languageLayout1, languageLayout2);
    }

    private void changeLanguage(Locale locale) {
        VaadinSession.getCurrent().setLocale(locale);
        updateUIText();
        getUI().ifPresent(ui -> ui.getPage().reload());
    }

    private void updateUIText() {
        logoutButton.setText(getMessage("logout.button"));
        profileLink.setText(getMessage("profile.page"));
        homeLink.setText(getMessage("home.page"));
    }

    private String getMessage(String code) {
        Locale currentLocale = VaadinSession.getCurrent().getLocale();
        return messageSource.getMessage(code, null, currentLocale);
    }
}