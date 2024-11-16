package com.example.easybankproject.languagetests;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KoreanTests {

    @Test
    public void testKoreanMessages() {
        // Set the locale to Korean
        Locale locale = new Locale("ko");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // General Messages
        assertEquals("재정적 자유로 가는 쉬운 길", messages.getString("welcome.message"));
        assertEquals("회원가입", messages.getString("register.button"));
        assertEquals("이미 계정이 있으신가요? 여기서 로그인하세요.", messages.getString("login.link"));
        assertEquals("계정이 없으신가요? 여기서 가입하세요.", messages.getString("register.link"));
        assertEquals("로그인", messages.getString("login.button"));
        assertEquals("사용자 이름", messages.getString("username.label"));
        assertEquals("이름", messages.getString("firstname.label"));
        assertEquals("성", messages.getString("lastname.label"));
        assertEquals("이메일", messages.getString("email.label"));
        assertEquals("전화번호", messages.getString("phonenumber.label"));
        assertEquals("주소", messages.getString("address.label"));
        assertEquals("비밀번호", messages.getString("password.label"));
        assertEquals("비밀번호 확인", messages.getString("confirm.password.label"));
        assertEquals("업데이트", messages.getString("update.button"));
        assertEquals("취소", messages.getString("cancel.button"));
        assertEquals("저장", messages.getString("save.button"));
        assertEquals("제출", messages.getString("submit.button"));

        // Error and Notification Messages
        assertEquals("오류: 사용자를 찾을 수 없습니다.", messages.getString("error.user.not.found"));
        assertEquals("오류: {0}", messages.getString("error.message"));
        assertEquals("프로필이 성공적으로 업데이트되었습니다.", messages.getString("profile.updated"));
        assertEquals("오류: 프로필을 찾을 수 없습니다.", messages.getString("profile.error"));
        assertEquals("필수 항목", messages.getString("required.field"));
        assertEquals("권한 없음: 세션에 토큰이 없습니다.", messages.getString("unauthorized.no.token"));
        assertEquals("서버 오류: {0}", messages.getString("server.error"));
        assertEquals("사용자 이름이 이미 존재합니다.", messages.getString("error.username.exists"));
        assertEquals("모든 필드를 입력하십시오.", messages.getString("error.empty.fields"));

        // Login & Registration Messages
        assertEquals("로그인 해주세요.", messages.getString("login.prompt"));
        assertEquals("잘못된 사용자 이름 또는 비밀번호입니다.", messages.getString("login.error"));
        assertEquals("새 계정 등록", messages.getString("register.title"));
        assertEquals("성공적으로 등록되었습니다! 로그인 해주세요.", messages.getString("register.success"));
        assertEquals("오류: 등록 중 오류가 발생했습니다.", messages.getString("register.error"));
        assertEquals("로그아웃", messages.getString("logout.button"));

        // Profile and Settings Messages
        assertEquals("프로필 업데이트", messages.getString("update.profile"));
        assertEquals("계정 설정", messages.getString("account.settings"));
        assertEquals("계정 삭제", messages.getString("delete.account"));
        assertEquals("계정이 성공적으로 삭제되었습니다.", messages.getString("delete.success"));

        // MainView Component Messages
        assertEquals("총 잔액:", messages.getString("balance.total"));
        assertEquals("+ 새 거래", messages.getString("new.transaction"));
        assertEquals("거래 내역:", messages.getString("transaction.history"));
        assertEquals("알림: {0}", messages.getString("notifications.link"));
        assertEquals("알림 아이콘", messages.getString("notifications.icon.alt"));
        assertEquals("거래 성공: {0}", messages.getString("transaction.success"));
        assertEquals("알림: {0}", messages.getString("notifications.title"));
        assertEquals("알림이 삭제되었습니다.", messages.getString("notification.deleted"));
        assertEquals("새 거래", messages.getString("transaction.new.button"));
        assertEquals("수신자를 찾을 수 없습니다.", messages.getString("sender.receiver.notfound"));
        assertEquals("자금이 부족합니다.", messages.getString("insufficient.funds"));

        // Labels and Titles
        assertEquals("금액 €", messages.getString("amount.label"));
        assertEquals("수신 계좌 ID", messages.getString("receiver.label"));
        assertEquals("메시지", messages.getString("message.label"));
        assertEquals("새 거래", messages.getString("transaction.new.title"));
        assertEquals("작업", messages.getString("actions.header"));
        assertEquals("금액", messages.getString("amount.header"));
        assertEquals("삭제", messages.getString("delete.button"));

        // Page Titles
        assertEquals("홈 페이지", messages.getString("home.page"));
        assertEquals("프로필 페이지", messages.getString("profile.page"));
        assertEquals("내 프로필", messages.getString("my.profile"));
        assertEquals("개인 정보", messages.getString("personal.information"));

        // Language Options
        assertEquals("언어 선택", messages.getString("language.selection"));
        assertEquals("영어", messages.getString("English"));
        assertEquals("한국어", messages.getString("Korean"));
        assertEquals("아랍어", messages.getString("Arabic"));
        assertEquals("핀", messages.getString("Finland"));

        // Notification Messages
        assertEquals("성공적으로 로그인되었습니다.", messages.getString("login.notification"));
        assertEquals("성공적으로 등록되었습니다.", messages.getString("register.notification"));
        assertEquals("메시지", messages.getString("message"));
        assertEquals("내용", messages.getString("content.title"));
        assertEquals("타임스탬프", messages.getString("timestamp.title"));
        assertEquals("신분증 로 거래가 생성되었습니다:", messages.getString("created.transaction"));
        assertEquals("로그인 성공!", messages.getString("login.success"));

        // Transaction Details
        assertEquals("타임스탬프", messages.getString("timestamp"));
        assertEquals("발신자", messages.getString("senderAccount"));
        assertEquals("수신자", messages.getString("receiverAccount"));
        assertEquals("신분증", messages.getString("transactionID"));
    }
}

