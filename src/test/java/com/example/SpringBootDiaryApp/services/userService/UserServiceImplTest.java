package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.AuthenticationRequest;
import com.example.SpringBootDiaryApp.data.dto.request.RegisterRequest;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.User;
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
       registerRequest1.setUserName("user101");
       registerRequest1.setEmail("user101@gmail.com");
       registerRequest1.setPassword("password1");

       registerRequest2 = new RegisterRequest();
       registerRequest2.setUserName("user102");
       registerRequest2.setEmail("user102@gmail.com");
       registerRequest2.setPassword("password2");
    }

    @Test
    void registerUser() {
        AuthenticationResponse response1 = userService.registerUser(registerRequest1);
        assertThat(response1.getMessage()).isEqualTo("User Registration Successful. Check your email to verify your account");
        assertThat(response1.isSuccess()).isEqualTo(true);
        AuthenticationResponse response2 = userService.registerUser(registerRequest2);
        assertThat(response2.getMessage()).isEqualTo("User Registration Successful. Check your email to verify your account");
        assertThat(response2.isSuccess()).isEqualTo(true);
    }

    @Test
    void verifyUser() {
    }

    @Test
    void authenticate() {
        AuthenticationRequest authenticationRequest1 = new AuthenticationRequest();
        authenticationRequest1.setEmail("user102@gmail.com");
        authenticationRequest1.setPassword("password");
    }

    @Test
    void uploadProfileImage() {
    }

    @Test
    void getUserById() {

    }

    @Test
    void getUserByEmail() {
        User foundUser = userService.getUserByEmail("user101@gmail.com");
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
        assertThat(foundUser.getPassword()).isEqualTo(registerRequest1.getPassword());
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