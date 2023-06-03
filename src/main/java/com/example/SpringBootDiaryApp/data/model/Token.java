package com.example.SpringBootDiaryApp.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String otp;
    @OneToOne
    private User user;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime expiryTime = createdAt.plusMinutes(5L);

    public Token(User user, String otp) {
        this.user = user;
        this.otp = otp;
    }
}
