package com.example.SpringBootDiaryApp.config.security.jwtToken;

import com.example.SpringBootDiaryApp.data.model.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JwtToken {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String jwtToken;
    @Enumerated(EnumType.STRING)
    private final JwtTokenType jwtTokenType = JwtTokenType.BEARER;
    private boolean isRevoked;
    private boolean isExpired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_jwt_id")
    private User user;
}
