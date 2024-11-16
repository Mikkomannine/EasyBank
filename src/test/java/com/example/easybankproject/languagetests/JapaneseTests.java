package com.example.easybankproject.languagetests;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JapaneseTests {

    @Test
    public void testJapaneseMessages() {
        // Set the locale to Japanese
        Locale locale = new Locale("ja");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // General Messages
        assertEquals("あなたの簡単な財務自由への道", messages.getString("welcome.message"));
        assertEquals("登録", messages.getString("register.button"));
        assertEquals("すでにアカウントをお持ちですか？ ここでログイン", messages.getString("login.link"));
        assertEquals("アカウントをお持ちでないですか？ ここで登録", messages.getString("register.link"));
        assertEquals("ログイン", messages.getString("login.button"));
        assertEquals("ユーザー名", messages.getString("username.label"));
        assertEquals("名", messages.getString("firstname.label"));
        assertEquals("姓", messages.getString("lastname.label"));
        assertEquals("メール", messages.getString("email.label"));
        assertEquals("電話番号", messages.getString("phonenumber.label"));
        assertEquals("住所", messages.getString("address.label"));
        assertEquals("パスワード", messages.getString("password.label"));
        assertEquals("更新", messages.getString("update.button"));
        assertEquals("キャンセル", messages.getString("cancel.button"));
        assertEquals("保存", messages.getString("save.button"));
        assertEquals("送信", messages.getString("submit.button"));

        // Error and Notification Messages
        assertEquals("エラー：ユーザーが見つかりません。", messages.getString("error.user.not.found"));
        assertEquals("エラー：{0}", messages.getString("error.message"));
        assertEquals("プロフィールが正常に更新されました。", messages.getString("profile.updated"));
        assertEquals("エラー：プロフィールが見つかりません。", messages.getString("profile.error"));
        assertEquals("必須フィールド", messages.getString("required.field"));
        assertEquals("未認証：セッションにトークンがありません。", messages.getString("unauthorized.no.token"));
        assertEquals("サーバーエラー：{0}", messages.getString("server.error"));
        assertEquals("ユーザー名はすでに存在します。", messages.getString("error.username.exists"));
        assertEquals("すべてのフィールドを入力してください。", messages.getString("error.empty.fields"));

        // Login & Registration Messages
        assertEquals("ログインしてください。", messages.getString("login.prompt"));
        assertEquals("無効なユーザー名またはパスワードです。", messages.getString("login.error"));
        assertEquals("新しいアカウントを登録", messages.getString("register.title"));
        assertEquals("正常に登録されました！ ログインしてください。", messages.getString("register.success"));
        assertEquals("エラー：登録中にエラーが発生しました。", messages.getString("register.error"));
        assertEquals("ログアウト", messages.getString("logout.button"));

        // Profile and Settings Messages
        assertEquals("プロフィールを更新", messages.getString("update.profile"));
        assertEquals("アカウント設定", messages.getString("account.settings"));
        assertEquals("アカウントを削除", messages.getString("delete.account"));
        assertEquals("アカウントが正常に削除されました。", messages.getString("delete.success"));

        // MainView Component Messages
        assertEquals("合計残高：", messages.getString("balance.total"));
        assertEquals("+ 新しい取引", messages.getString("new.transaction"));
        assertEquals("取引履歴：", messages.getString("transaction.history"));
        assertEquals("通知: {0}", messages.getString("notifications.link"));
        assertEquals("通知アイコン", messages.getString("notifications.icon.alt"));
        assertEquals("取引成功：{0}", messages.getString("transaction.success"));
        assertEquals("通知: {0}", messages.getString("notifications.title"));
        assertEquals("通知が削除されました。", messages.getString("notification.deleted"));
        assertEquals("新しい取引", messages.getString("transaction.new.button"));
        assertEquals("受信者が見つかりません。", messages.getString("sender.receiver.notfound"));
        assertEquals("資金不足。", messages.getString("insufficient.funds"));

        // Labels and Titles
        assertEquals("金額 €", messages.getString("amount.label"));
        assertEquals("受取人アカウントID", messages.getString("receiver.label"));
        assertEquals("メッセージ", messages.getString("message.label"));
        assertEquals("新しい取引", messages.getString("transaction.new.title"));
        assertEquals("アクション", messages.getString("actions.header"));
        assertEquals("金額", messages.getString("amount.header"));
        assertEquals("新しい取引", messages.getString("transaction.new.button"));
        assertEquals("削除", messages.getString("delete.button"));

        // Page Titles
        assertEquals("ホーム", messages.getString("home.page"));
        assertEquals("プロフィール", messages.getString("profile.page"));
        assertEquals("削除", messages.getString("delete.button"));
        assertEquals("私のプロフィール", messages.getString("my.profile"));
        assertEquals("個人情報", messages.getString("personal.information"));

        // Language Options
        assertEquals("言語を選択", messages.getString("language.selection"));
        assertEquals("英語", messages.getString("English"));
        assertEquals("フィンランド語", messages.getString("Finland"));
        assertEquals("韓国語", messages.getString("Korean"));
        assertEquals("アラビア語", messages.getString("Arabic"));

        // Notifications
        assertEquals("正常にログインしました。", messages.getString("login.notification"));
        assertEquals("正常に登録されました。", messages.getString("register.notification"));
        assertEquals("コンテンツ", messages.getString("content.title"));
        assertEquals("タイムスタンプ", messages.getString("timestamp.title"));
        assertEquals("識別で取引が作成されました：", messages.getString("created.transaction"));
        assertEquals("ログインに成功しました！", messages.getString("login.success"));

        // Transaction Details
        assertEquals("メッセージ", messages.getString("message"));
        assertEquals("タイムスタンプ", messages.getString("timestamp"));
        assertEquals("送信者", messages.getString("senderAccount"));
        assertEquals("受信者", messages.getString("receiverAccount"));
        assertEquals("識別", messages.getString("transactionID"));
    }
}
