package com.example.SpringBootDiaryApp.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthenticationResponse {
    private String message;
    private boolean isSuccess;
//    private String accessToken;
//    private String refreshToken;
}
