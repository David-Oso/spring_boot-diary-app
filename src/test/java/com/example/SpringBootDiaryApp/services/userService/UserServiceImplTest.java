package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.RegisterRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateUserRequest;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.example.SpringBootDiaryApp.utils.AppUtils.DIARY_TEST_IMAGE;
import static com.example.SpringBootDiaryApp.utils.AppUtils.UPDATE_TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserServiceImplTest {
    @Autowired private UserService userService;
    private RegisterRequest registerRequest1;
    private RegisterRequest registerRequest2;
    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    void setUp() {
        registerRequest1 = new RegisterRequest();
        registerRequest1.setEmail("email1");
        registerRequest1.setUserName("user1");
        registerRequest1.setPassword("password1");
        registerRequest1.setProfileImage(uploadTestImage(DIARY_TEST_IMAGE));

        registerRequest2 = new RegisterRequest();
        registerRequest2.setEmail("email2");
        registerRequest2.setUserName("user2");
        registerRequest2.setPassword("password2");
        registerRequest2.setProfileImage(uploadTestImage(DIARY_TEST_IMAGE));

        updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserName("updateUserName");
        updateUserRequest.setProfileImage(uploadTestImage(UPDATE_TEST_IMAGE));
    }

    private static MultipartFile uploadTestImage(String imageUrl) {
        MultipartFile file;
        try{
        file = new MockMultipartFile("test_image",
                new FileInputStream(imageUrl));
        return file;
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Test
    void registerUser() {
        AuthenticationResponse response = userService.registerUser(registerRequest1);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("User Registration Successful");
        AuthenticationResponse response2 = userService.registerUser(registerRequest2);
        assertThat(response2).isNotNull();
        assertThat(response2.getMessage()).isEqualTo("User Registration Successful");
    }

    @Test
    void authenticate() {
    }

    @Test
    void getUserById() {
        User foundUser = userService.getUserById(1L);
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
    }

    @Test
    void getUserByEmail() {
        User foundUser = userService.getUserByEmail("email1");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
    }

    @Test
    void updateUser() {
        updateUserRequest.setUserId(1L);
        String response = userService.updateUser(updateUserRequest);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("User Details updated");
    }

    @Test
    void count() {
        assertThat(userService.count()).isEqualTo(2);
    }

    @Test
    void deleteUserById() {
        String response = userService.deleteUserById(2L);
        assertThat(response).isEqualTo("User Account Deleted");
    }
}