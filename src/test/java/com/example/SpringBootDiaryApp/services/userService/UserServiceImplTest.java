package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.EmailVerificationRequest;
import com.example.SpringBootDiaryApp.data.dto.request.RegisterRequest;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired UserService userService;
    private RegisterRequest registerRequest1;
    private RegisterRequest registerRequest2;

    @BeforeEach
    void setUp() {
        registerRequest1 = new RegisterRequest();
        registerRequest1.setUserName("user");
        registerRequest1.setEmail("osodavid001@gmail.com");
        registerRequest1.setPassword("password");

        registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("secondUser");
        registerRequest2.setEmail("osodavid272@gmail.com");
        registerRequest2.setPassword("password");
    }

    @Test
    void registerUser() {
        userService.registerUser(registerRequest1);
        userService.registerUser(registerRequest2);
    }

    @Test
    void verifyEmail() {
        EmailVerificationRequest emailVerificationRequest = new EmailVerificationRequest();
        emailVerificationRequest.setEmail("osodavid001@gmail.com");
        emailVerificationRequest.setVerificationToken("-_zbJrUU");

        AuthenticationResponse response = userService.verifyEmail(emailVerificationRequest, registerRequest1);
        assertThat(response.getMessage()).isEqualTo("Account verification successful");
        assertThat(response.isSuccess()).isEqualTo(true);

        EmailVerificationRequest emailVerificationRequest2 = new EmailVerificationRequest();
        emailVerificationRequest2.setEmail("osodavid272@gmail.com");
        emailVerificationRequest2.setVerificationToken("kUZy89Sq");

        AuthenticationResponse response2 = userService.verifyEmail(emailVerificationRequest2, registerRequest2);
        assertThat(response2.getMessage()).isEqualTo("Account verification successful");
        assertThat(response2.isSuccess()).isEqualTo(true);
    }

    @Test
    void authenticate() {
    }

    @Test
    void uploadProfileImage() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void count() {
    }
}