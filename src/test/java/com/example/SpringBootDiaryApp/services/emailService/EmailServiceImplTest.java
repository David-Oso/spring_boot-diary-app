package com.example.SpringBootDiaryApp.services.emailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class EmailServiceImplTest {
    @Autowired EmailService emailService;
    private EmailNotificationRequest emailNotificationRequest;

    @BeforeEach
    void setUp() {
        emailNotificationRequest = new EmailNotificationRequest();
        emailNotificationRequest.setSubject("Testing email");
        emailNotificationRequest.setHtmlContent("This is to confirm that this mail service is working");
        List<Recipient> recipients = new ArrayList<>();
        recipients.add(new Recipient("diary", "osodavid001@gmail.com"));
        emailNotificationRequest.setTo(recipients);
    }

    @Test
    void sendHtmlMail() {
        String response = emailService.sendHtmlMail(emailNotificationRequest);
        System.out.println(response);
    }
}