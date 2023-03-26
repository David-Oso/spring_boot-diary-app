package com.example.SpringBootDiaryApp.data.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private Long id;
    private String message;
    private Boolean isUnlocked;
}
