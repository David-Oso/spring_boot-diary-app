package com.example.SpringBootDiaryApp.services.emailService;

public interface EmailService {
    String sendHtmlMail(EmailNotificationRequest request);
}
