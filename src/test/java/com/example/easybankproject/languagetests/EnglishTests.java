package com.example.easybankproject.languagetests;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnglishTests {

    @Test
    public void testEnglishMessages() {
        Locale locale = new Locale("en");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // General Messages
        assertEquals("Your Easy Path to Financial Freedom", messages.getString("welcome.message"));
        assertEquals("Register", messages.getString("register.button"));
        assertEquals("Already have an account? Login here.", messages.getString("login.link"));
        assertEquals("Don't have an account? Register here.", messages.getString("register.link"));
        assertEquals("Login", messages.getString("login.button"));
        assertEquals("Username", messages.getString("username.label"));
        assertEquals("First Name", messages.getString("firstname.label"));
        assertEquals("Last Name", messages.getString("lastname.label"));
        assertEquals("Email", messages.getString("email.label"));
        assertEquals("Phone Number", messages.getString("phonenumber.label"));
        assertEquals("Address", messages.getString("address.label"));
        assertEquals("Password", messages.getString("password.label"));
        assertEquals("Update", messages.getString("update.button"));
        assertEquals("Cancel", messages.getString("cancel.button"));
        assertEquals("Save", messages.getString("save.button"));
        assertEquals("Submit", messages.getString("submit.button"));

        // Error and Notification Messages
        assertEquals("Error: User not found.", messages.getString("error.user.not.found"));
        assertEquals("Error: {0}", messages.getString("error.message"));
        assertEquals("Profile successfully updated.", messages.getString("profile.updated"));
        assertEquals("Error: Profile not found.", messages.getString("profile.error"));
        assertEquals("Required field", messages.getString("required.field"));
        assertEquals("Unauthorized: No token in session.", messages.getString("unauthorized.no.token"));
        assertEquals("Server Error: {0}", messages.getString("server.error"));
        assertEquals("Username already exists.", messages.getString("error.username.exists"));
        assertEquals("Please fill in all fields.", messages.getString("error.empty.fields"));

        // Login & Registration Messages
        assertEquals("Please log in.", messages.getString("login.prompt"));
        assertEquals("Invalid username or password.", messages.getString("login.error"));
        assertEquals("Register a New Account", messages.getString("register.title"));
        assertEquals("Registered successfully! Please log in.", messages.getString("register.success"));
        assertEquals("Error: An error occurred during registration.", messages.getString("register.error"));
        assertEquals("Logout", messages.getString("logout.button"));

        // Profile and Settings Messages
        assertEquals("Update Profile", messages.getString("update.profile"));

        // MainView Component Messages
        assertEquals("Total Balance:", messages.getString("balance.total"));
        assertEquals("+ New Transaction", messages.getString("new.transaction"));
        assertEquals("Transaction History:", messages.getString("transaction.history"));
        assertEquals("Notifications: {0}", messages.getString("notifications.link"));
        assertEquals("Notification Icon", messages.getString("notifications.icon.alt"));
        assertEquals("Transaction successful: {0}", messages.getString("transaction.success"));
        assertEquals("Notifications: {0}", messages.getString("notifications.title"));
        assertEquals("Notification deleted.", messages.getString("notification.deleted"));
        assertEquals("New Transaction", messages.getString("transaction.new.button"));
        assertEquals("Receiver not found.", messages.getString("sender.receiver.notfound"));
        assertEquals("Insufficient funds.", messages.getString("insufficient.funds"));

        // Labels and Titles
        assertEquals("Amount €", messages.getString("amount.label"));
        assertEquals("Receiver Account ID", messages.getString("receiver.label"));
        assertEquals("Message", messages.getString("message.label"));
        assertEquals("New Transaction", messages.getString("transaction.new.title"));
        assertEquals("Actions", messages.getString("actions.header"));
        assertEquals("Amount", messages.getString("amount.header"));

        // Page Titles
        assertEquals("Home", messages.getString("home.page"));
        assertEquals("Profile", messages.getString("profile.page"));
        assertEquals("Delete", messages.getString("delete.button"));
        assertEquals("My Profile", messages.getString("my.profile"));
        assertEquals("Personal Information", messages.getString("personal.information"));

        // Language Options
        assertEquals("Select Language", messages.getString("language.selection"));
        assertEquals("English", messages.getString("English"));
        assertEquals("Korean", messages.getString("Korean"));
        assertEquals("Finnish", messages.getString("Finland"));
        assertEquals("Arabic", messages.getString("Arabic"));

        // Notification Messages
        assertEquals("received from user: ", messages.getString("from.notification"));
        assertEquals("sent to account: ", messages.getString("to.notification"));
        assertEquals("logged in successfully.", messages.getString("login.notification"));
        assertEquals("registered successfully.", messages.getString("register.notification"));
        assertEquals("Content", messages.getString("content.title"));
        assertEquals("Timestamp", messages.getString("timestamp.title"));
        assertEquals("Transaction created with ID:", messages.getString("created.transaction"));
        assertEquals("Login successful!", messages.getString("login.success"));

        // Transaction details
        assertEquals("Message", messages.getString("message"));
        assertEquals("Timestamp", messages.getString("timestamp"));
        assertEquals("sender", messages.getString("senderAccount"));
        assertEquals("receiver", messages.getString("receiverAccount"));
        assertEquals("ID", messages.getString("transactionID"));
    }
}

