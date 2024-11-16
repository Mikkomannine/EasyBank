package com.example.easybankproject.languagetests;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArabicTests {

    @Test
    public void testArabicMessages() {
        Locale locale = new Locale("ar", "SA");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // General Messages
        assertEquals("طريقك السهل نحو الحرية المالية", messages.getString("welcome.message"));
        assertEquals("تسجيل", messages.getString("register.button"));
        assertEquals("لديك حساب بالفعل؟ سجل الدخول هنا.", messages.getString("login.link"));
        assertEquals("ليس لديك حساب؟ سجل هنا.", messages.getString("register.link"));
        assertEquals("تسجيل الدخول", messages.getString("login.button"));
        assertEquals("اسم المستخدم", messages.getString("username.label"));
        assertEquals("الاسم الأول", messages.getString("firstname.label"));
        assertEquals("اسم العائلة", messages.getString("lastname.label"));
        assertEquals("البريد الإلكتروني", messages.getString("email.label"));
        assertEquals("رقم الهاتف", messages.getString("phonenumber.label"));
        assertEquals("العنوان", messages.getString("address.label"));
        assertEquals("كلمة المرور", messages.getString("password.label"));
        assertEquals("تأكيد كلمة المرور", messages.getString("confirm.password.label"));
        assertEquals("تحديث", messages.getString("update.button"));
        assertEquals("إلغاء", messages.getString("cancel.button"));
        assertEquals("حفظ", messages.getString("save.button"));
        assertEquals("إرسال", messages.getString("submit.button"));
        assertEquals("تحديث الملف الشخصي", messages.getString("update.profile"));

        // Error and Notification Messages
        assertEquals("خطأ: لم يتم العثور على المستخدم.", messages.getString("error.user.not.found"));
        assertEquals("خطأ: {0}", messages.getString("error.message"));
        assertEquals("تم تحديث الملف الشخصي بنجاح.", messages.getString("profile.updated"));
        assertEquals("خطأ: لم يتم العثور على الملف الشخصي.", messages.getString("profile.error"));
        assertEquals("حقل مطلوب", messages.getString("required.field"));
        assertEquals("غير مصرح: لا يوجد رمز في الجلسة.", messages.getString("unauthorized.no.token"));
        assertEquals("خطأ في الخادم: {0}", messages.getString("server.error"));
        assertEquals("اسم المستخدم موجود بالفعل.", messages.getString("error.username.exists"));
        assertEquals("يرجى ملء جميع الحقول.", messages.getString("error.empty.fields"));

        // Login & Registration Messages
        assertEquals("يرجى تسجيل الدخول.", messages.getString("login.prompt"));
        assertEquals("اسم المستخدم أو كلمة المرور غير صحيحة.", messages.getString("login.error"));
        assertEquals("تسجيل حساب جديد", messages.getString("register.title"));
        assertEquals("تم التسجيل بنجاح! يرجى تسجيل الدخول.", messages.getString("register.success"));
        assertEquals("خطأ: حدث خطأ أثناء التسجيل.", messages.getString("register.error"));
        assertEquals("تسجيل الخروج", messages.getString("logout.button"));

        // MainView Component Messages
        assertEquals("الرصيد الكلي:", messages.getString("balance.total"));
        assertEquals("+ معاملة جديدة", messages.getString("new.transaction"));
        assertEquals("تاريخ المعاملات:", messages.getString("transaction.history"));
        assertEquals("الإشعارات: {0}", messages.getString("notifications.link"));
        assertEquals("أيقونة الإشعار", messages.getString("notifications.icon.alt"));
        assertEquals("المعاملة ناجحة: {0}", messages.getString("transaction.success"));
        assertEquals("الإشعارات: {0}", messages.getString("notifications.title"));
        assertEquals("تم حذف الإشعار.", messages.getString("notification.deleted"));
        assertEquals("معاملة جديدة", messages.getString("transaction.new.button"));
        assertEquals("المستلم غير موجود.", messages.getString("sender.receiver.notfound"));
        assertEquals("الأموال غير كافية.", messages.getString("insufficient.funds"));

        // Labels and Titles
        assertEquals("المبلغ €", messages.getString("amount.label"));
        assertEquals("معرف حساب المستلم", messages.getString("receiver.label"));
        assertEquals("الرسالة", messages.getString("message.label"));
        assertEquals("معاملة جديدة", messages.getString("transaction.new.title"));
        assertEquals("الإجراءات", messages.getString("actions.header"));
        assertEquals("المبلغ", messages.getString("amount.header"));
        assertEquals("حذف", messages.getString("delete.button"));

        // Page Titles
        assertEquals("بيت", messages.getString("home.page"));
        assertEquals("حساب تعريفي", messages.getString("profile.page"));
        assertEquals("مل", messages.getString("my.profile"));
        assertEquals("معلومات شخصية", messages.getString("personal.information"));

        // Notifications
        assertEquals("تم الاستلام من المستخدم: ", messages.getString("from.notification"));
        assertEquals("تم الإرسال إلى الحساب: ", messages.getString("to.notification"));
        assertEquals("تم تسجيل الدخول بنجاح.", messages.getString("login.notification"));
        assertEquals("تم التسجيل بنجاح.", messages.getString("register.notification"));
        assertEquals("المحتوى", messages.getString("content.title"));
        assertEquals("الطابع الزمني", messages.getString("timestamp.title"));
        assertEquals("تم إنشاء المعاملة بالمعرف:", messages.getString("created.transaction"));
        assertEquals("تم تسجيل الدخول بنجاح!", messages.getString("login.success"));

        // Transaction details
        assertEquals("رسالة", messages.getString("message"));
        assertEquals("الطابع الزمني", messages.getString("timestamp"));
        assertEquals("حساب المرسل", messages.getString("senderAccount"));
        assertEquals("حساب المستلم", messages.getString("receiverAccount"));
        assertEquals("معرف", messages.getString("transactionID"));
    }
}


