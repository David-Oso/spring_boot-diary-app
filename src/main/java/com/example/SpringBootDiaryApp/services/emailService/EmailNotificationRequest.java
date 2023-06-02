package com.example.SpringBootDiaryApp.services.emailService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationRequest {
    private final Sender sender = new Sender("my_diary", "noreply@diary.net");
    private List<Recipient> to = new ArrayList<>();
    private String subject;
    private String htmlContent;
}
