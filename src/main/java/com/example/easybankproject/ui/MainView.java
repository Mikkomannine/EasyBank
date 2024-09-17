package com.example.easybankproject.ui;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

@PageTitle("Main")
@Route(value = "main")
public class MainView extends Composite<VerticalLayout> {

    public MainView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Icon icon = new Icon();
        Button buttonPrimary = new Button();
        Button buttonPrimary2 = new Button();
        Button buttonPrimary3 = new Button();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        Paragraph textLarge = new Paragraph();
        H1 h1 = new H1();
        Button buttonPrimary4 = new Button();
        H2 h2 = new H2();
        Tabs tabs = new Tabs();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button createaccount = new Button("Create Account", event -> createButton());
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
        icon.setIcon("lumo:user");
        buttonPrimary.setText("Profile");
        buttonPrimary.setWidth("119px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.setText("Notifications");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary3.setText("History");
        buttonPrimary3.setWidth("119px");
        buttonPrimary3.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutColumn3.addClassName(Padding.XLARGE);
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn3.setHeight("100%");
        layoutColumn3.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn3.setAlignItems(Alignment.CENTER);
        textLarge.setText("Total Balance:");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        h1.setText("1902.32 €");
        layoutColumn3.setAlignSelf(Alignment.START, h1);
        h1.setWidth("max-content");
        buttonPrimary4.setText("+ New Payment");
        layoutColumn3.setAlignSelf(Alignment.START, buttonPrimary4);
        buttonPrimary4.setWidth("min-content");
        buttonPrimary4.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h2.setText("My Accounts:");
        layoutColumn3.setAlignSelf(Alignment.START, h2);
        h2.setWidth("max-content");
        tabs.setWidth("100%");
        setTabsSampleData(tabs);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(icon);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(buttonPrimary2);
        layoutColumn2.add(buttonPrimary3);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(textLarge);
        layoutColumn3.add(h1);
        layoutColumn3.add(buttonPrimary4);
        layoutColumn3.add(createaccount);
        layoutColumn3.add(h2);
        layoutColumn3.add(tabs);
        getContent().add(layoutRow2);

    }

    private void setTabsSampleData(Tabs tabs) {
        tabs.add(new Tab("Main Account"));
        tabs.add(new Tab("Savings"));
    }
private void createButton() {
    getUI().ifPresent(ui -> ui.navigate("create-account"));
}

}


