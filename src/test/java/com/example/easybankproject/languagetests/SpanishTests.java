package com.example.easybankproject.languagetests;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpanishTests {

    @Test
    public void testSpanishMessages() {
        // Set the locale to Spanish
        Locale locale = new Locale("es");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // General Messages
        assertEquals("Tu camino fácil hacia la libertad financiera", messages.getString("welcome.message"));
        assertEquals("Registrarse", messages.getString("register.button"));
        assertEquals("¿Ya tienes una cuenta? Inicia sesión aquí.", messages.getString("login.link"));
        assertEquals("¿No tienes una cuenta? Regístrate aquí.", messages.getString("register.link"));
        assertEquals("Iniciar sesión", messages.getString("login.button"));
        assertEquals("Nombre de usuario", messages.getString("username.label"));
        assertEquals("Nombre", messages.getString("firstname.label"));
        assertEquals("Apellido", messages.getString("lastname.label"));
        assertEquals("Correo electrónico", messages.getString("email.label"));
        assertEquals("Número de teléfono", messages.getString("phonenumber.label"));
        assertEquals("Dirección", messages.getString("address.label"));
        assertEquals("Contraseña", messages.getString("password.label"));
        assertEquals("Actualizar", messages.getString("update.button"));
        assertEquals("Cancelar", messages.getString("cancel.button"));
        assertEquals("Guardar", messages.getString("save.button"));
        assertEquals("Enviar", messages.getString("submit.button"));

        // Error and Notification Messages
        assertEquals("Error: Usuario no encontrado.", messages.getString("error.user.not.found"));
        assertEquals("Error: {0}", messages.getString("error.message"));
        assertEquals("Perfil actualizado con éxito.", messages.getString("profile.updated"));
        assertEquals("Error: Perfil no encontrado.", messages.getString("profile.error"));
        assertEquals("Campo requerido", messages.getString("required.field"));
        assertEquals("No autorizado: Sin token en la sesión.", messages.getString("unauthorized.no.token"));
        assertEquals("Error del servidor: {0}", messages.getString("server.error"));
        assertEquals("El nombre de usuario ya existe.", messages.getString("error.username.exists"));
        assertEquals("Por favor, complete todos los campos.", messages.getString("error.empty.fields"));

        // Login & Registration Messages
        assertEquals("Por favor inicia sesión.", messages.getString("login.prompt"));
        assertEquals("Nombre de usuario o contraseña inválidos.", messages.getString("login.error"));
        assertEquals("Registrar una nueva cuenta", messages.getString("register.title"));
        assertEquals("¡Registrado con éxito! Por favor inicia sesión.", messages.getString("register.success"));
        assertEquals("Error: Ocurrió un error durante el registro.", messages.getString("register.error"));
        assertEquals("Cerrar sesión", messages.getString("logout.button"));

        // Profile and Settings Messages
        assertEquals("Actualizar perfil", messages.getString("update.profile"));
        assertEquals("Configuración de la cuenta", messages.getString("account.settings"));
        assertEquals("Eliminar cuenta", messages.getString("delete.account"));
        assertEquals("Cuenta eliminada con éxito.", messages.getString("delete.success"));

        // MainView Component Messages
        assertEquals("Saldo total:", messages.getString("balance.total"));
        assertEquals("+ Nueva transacción", messages.getString("new.transaction"));
        assertEquals("Historial de transacciones:", messages.getString("transaction.history"));
        assertEquals("Notificaciones: {0}", messages.getString("notifications.link"));
        assertEquals("Ícono de notificación", messages.getString("notifications.icon.alt"));
        assertEquals("Transacción exitosa: {0}", messages.getString("transaction.success"));
        assertEquals("Notificaciones: {0}", messages.getString("notifications.title"));
        assertEquals("Notificación eliminada.", messages.getString("notification.deleted"));
        assertEquals("Nueva transacción", messages.getString("transaction.new.button"));
        assertEquals("Receptor no encontrado.", messages.getString("sender.receiver.notfound"));
        assertEquals("Fondos insuficientes.", messages.getString("insufficient.funds"));

        // Labels and Titles
        assertEquals("Monto €", messages.getString("amount.label"));
        assertEquals("ID de cuenta receptora", messages.getString("receiver.label"));
        assertEquals("Mensaje", messages.getString("message.label"));
        assertEquals("Nueva transacción", messages.getString("transaction.new.title"));
        assertEquals("Acciones", messages.getString("actions.header"));
        assertEquals("Monto", messages.getString("amount.header"));

        // Page Titles
        assertEquals("Inicio", messages.getString("home.page"));
        assertEquals("Perfil", messages.getString("profile.page"));
        assertEquals("Eliminar", messages.getString("delete.button"));
        assertEquals("Mi perfil", messages.getString("my.profile"));
        assertEquals("Información personal", messages.getString("personal.information"));

        // Language Options
        assertEquals("Seleccionar idioma", messages.getString("language.selection"));
        assertEquals("Inglés", messages.getString("English"));
        assertEquals("Coreano", messages.getString("Korean"));
        assertEquals("Finlandés", messages.getString("Finland"));
        assertEquals("Árabe", messages.getString("Arabic"));
        assertEquals("Español", messages.getString("Spanish"));
        assertEquals("Japonés", messages.getString("Japanese"));
        assertEquals("Finlandés", messages.getString("Finnish"));

        // Notifications
        assertEquals("recibido de usuario: ", messages.getString("from.notification"));
        assertEquals("enviado a cuenta: ", messages.getString("to.notification"));
        assertEquals("iniciado sesión correctamente.", messages.getString("login.notification"));
        assertEquals("registrado correctamente.", messages.getString("register.notification"));
        assertEquals("contenido", messages.getString("content.title"));
        assertEquals("marca de tiempo", messages.getString("timestamp.title"));
        assertEquals("Transacción creada con ID:", messages.getString("created.transaction"));
        assertEquals("¡Inicio de sesión exitoso!", messages.getString("login.success"));

        // Transaction Details
        assertEquals("mensaje", messages.getString("message"));
        assertEquals("Marca de tiempo", messages.getString("timestamp"));
        assertEquals("remitente", messages.getString("senderAccount"));
        assertEquals("receptor", messages.getString("receiverAccount"));
        assertEquals("ID", messages.getString("transactionID"));
    }
}

