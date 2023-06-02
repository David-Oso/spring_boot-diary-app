package com.example.SpringBootDiaryApp.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MailConfig {
    private String apiKey;
    private String mailUrl;
}
