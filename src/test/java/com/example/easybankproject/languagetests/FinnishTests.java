package com.example.easybankproject.languagetests;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinnishTests {

    @Test
    public void testFinnishMessages() {
        // Set the locale to Finnish
        Locale locale = new Locale("fi");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // General Messages
        assertEquals("Helppo polku taloudelliseen vapauteen", messages.getString("welcome.message"));
        assertEquals("Rekisteröidy", messages.getString("register.button"));
        assertEquals("Onko sinulla jo tili? Kirjaudu tästä.", messages.getString("login.link"));
        assertEquals("Eikö sinulla ole tiliä? Rekisteröidy tästä.", messages.getString("register.link"));
        assertEquals("Kirjaudu sisään", messages.getString("login.button"));
        assertEquals("Käyttäjätunnus", messages.getString("username.label"));
        assertEquals("Etunimi", messages.getString("firstname.label"));
        assertEquals("Sukunimi", messages.getString("lastname.label"));
        assertEquals("Sähköposti", messages.getString("email.label"));
        assertEquals("Puhelinnumero", messages.getString("phonenumber.label"));
        assertEquals("Osoite", messages.getString("address.label"));
        assertEquals("Salasana", messages.getString("password.label"));
        assertEquals("Vahvista salasana", messages.getString("confirm.password.label"));
        assertEquals("Päivitä", messages.getString("update.button"));
        assertEquals("Peruuta", messages.getString("cancel.button"));
        assertEquals("Tallenna", messages.getString("save.button"));
        assertEquals("Lähetä", messages.getString("submit.button"));

        // Error and Notification Messages
        assertEquals("Virhe: Käyttäjää ei löytynyt.", messages.getString("error.user.not.found"));
        assertEquals("Virhe: {0}", messages.getString("error.message"));
        assertEquals("Profiili päivitetty onnistuneesti.", messages.getString("profile.updated"));
        assertEquals("Virhe: Profiilia ei löytynyt.", messages.getString("profile.error"));
        assertEquals("Pakollinen kenttä", messages.getString("required.field"));
        assertEquals("Ei oikeutta: Ei istuntoa.", messages.getString("unauthorized.no.token"));
        assertEquals("Palvelinvirhe: {0}", messages.getString("server.error"));
        assertEquals("Käyttäjätunnus on jo olemassa.", messages.getString("error.username.exists"));
        assertEquals("Täytä kaikki kentät.", messages.getString("error.empty.fields"));

        // Login & Registration Messages
        assertEquals("Ole hyvä ja kirjaudu sisään.", messages.getString("login.prompt"));
        assertEquals("Virheellinen käyttäjätunnus tai salasana.", messages.getString("login.error"));
        assertEquals("Rekisteröidy uusi tili", messages.getString("register.title"));
        assertEquals("Rekisteröinti onnistui! Kirjaudu sisään.", messages.getString("register.success"));
        assertEquals("Virhe: Tapahtui virhe rekisteröinnin aikana.", messages.getString("register.error"));
        assertEquals("Kirjaudu ulos", messages.getString("logout.button"));

        // Profile and Settings Messages
        assertEquals("Päivitä profiili", messages.getString("update.profile"));
        assertEquals("Tilin asetukset", messages.getString("account.settings"));
        assertEquals("Poista tili", messages.getString("delete.account"));
        assertEquals("Tili poistettu onnistuneesti.", messages.getString("delete.success"));

        // MainView Component Messages
        assertEquals("Kokonaissaldo:", messages.getString("balance.total"));
        assertEquals("+ Uusi tapahtuma", messages.getString("new.transaction"));
        assertEquals("Tapahtumahistoria:", messages.getString("transaction.history"));
        assertEquals("Ilmoitukset: {0}", messages.getString("notifications.link"));
        assertEquals("Ilmoituskuvake", messages.getString("notifications.icon.alt"));
        assertEquals("Tapahtuma onnistui: {0}", messages.getString("transaction.success"));
        assertEquals("Ilmoitukset: {0}", messages.getString("notifications.title"));
        assertEquals("Ilmoitus poistettu.", messages.getString("notification.deleted"));
        assertEquals("Uusi Maksu", messages.getString("transaction.new.button"));
        assertEquals("Vastaanottajaa ei löytynyt.", messages.getString("sender.receiver.notfound"));
        assertEquals("Riittämättömät varat.", messages.getString("insufficient.funds"));

        // Labels and Titles
        assertEquals("Summa €", messages.getString("amount.label"));
        assertEquals("Vastaanottajan tilitunnus", messages.getString("receiver.label"));
        assertEquals("Viesti", messages.getString("message.label"));
        assertEquals("Uusi tapahtuma", messages.getString("transaction.new.title"));
        assertEquals("Toiminnot", messages.getString("actions.header"));
        assertEquals("Summa", messages.getString("amount.header"));
        assertEquals("Uusi Maksu", messages.getString("transaction.new.button"));
        assertEquals("Poista", messages.getString("delete.button"));

        // Page Titles
        assertEquals("Kotisivu", messages.getString("home.page"));
        assertEquals("Profiili", messages.getString("profile.page"));
        assertEquals("Poista", messages.getString("delete.button"));
        assertEquals("Profiilini", messages.getString("my.profile"));
        assertEquals("Henkilötiedot", messages.getString("personal.information"));

        // Language Options
        assertEquals("Valitse kieli", messages.getString("language.selection"));
        assertEquals("Englanti", messages.getString("English"));
        assertEquals("Suomi", messages.getString("Finland"));
        assertEquals("Korea", messages.getString("Korean"));
        assertEquals("Arabia", messages.getString("Arabic"));
        assertEquals("Espanja", messages.getString("Spanish"));
        assertEquals("Japani", messages.getString("Japanese"));

        // Notifications
        assertEquals("sait käyttäjältä:", messages.getString("from.notification"));
        assertEquals("lähetit tilille:", messages.getString("to.notification"));
        assertEquals("kirjautui sisään onnistuneesti.", messages.getString("login.notification"));
        assertEquals("rekisteröityi onnistuneesti.", messages.getString("register.notification"));
        assertEquals("Sisältö", messages.getString("content.title"));
        assertEquals("Aikaleima", messages.getString("timestamp.title"));
        assertEquals("Maksutapahtuma luotu ID:llä:", messages.getString("created.transaction"));
        assertEquals("Kirjautuminen onnistui!", messages.getString("login.success"));

        // Transaction Details
        assertEquals("viesti", messages.getString("message"));
        assertEquals("Aikaleima", messages.getString("timestamp"));
        assertEquals("lähettäjä", messages.getString("senderAccount"));
        assertEquals("vastaanottaja", messages.getString("receiverAccount"));
        assertEquals("ID", messages.getString("transactionID"));
    }
}

