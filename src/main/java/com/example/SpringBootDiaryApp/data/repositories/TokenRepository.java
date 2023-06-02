package com.example.SpringBootDiaryApp.data.repositories;

import com.example.SpringBootDiaryApp.data.model.Token;
import com.example.SpringBootDiaryApp.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByUserAndOtp(User user, String otp);
    Optional<Token> findTokenByUser(User user);
}
