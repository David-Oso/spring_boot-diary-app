package com.example.SpringBootDiaryApp.config;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator {
    public static String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

    }
}
