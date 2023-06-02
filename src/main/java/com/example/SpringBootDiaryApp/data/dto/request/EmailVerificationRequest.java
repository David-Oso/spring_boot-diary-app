package com.example.SpringBootDiaryApp.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailVerificationRequest {
    private String email;
    private String verificationToken;
    private String userName;
    private String password;
}
