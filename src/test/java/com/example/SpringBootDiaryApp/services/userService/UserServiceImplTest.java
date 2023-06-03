package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.ChangeUserNameRequest;
import com.example.SpringBootDiaryApp.data.dto.request.EmailVerificationRequest;
import com.example.SpringBootDiaryApp.data.dto.request.RegisterRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UploadImageRequest;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.example.SpringBootDiaryApp.utils.AppUtils.DIARY_TEST_IMAGE;
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
        UploadImageRequest uploadImageRequest = new UploadImageRequest();
        uploadImageRequest.setUserId(1L);
        try{
        MockMultipartFile file =
                new MockMultipartFile("test_license",
                        new FileInputStream(DIARY_TEST_IMAGE));
        uploadImageRequest.setProfileImage(file);
        String response = userService.uploadProfileImage(uploadImageRequest);
        assertThat(response).isEqualTo("Profile image uploaded");
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Test
    void getUserById() {
        User foundUser = userService.getUserById(1L);
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
        assertThat(foundUser.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void getUserByEmail() {
        User foundUser = userService.getUserByEmail("osodavid001@gmail.com");
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
    }

    @Test
    void changeUserName() {
        ChangeUserNameRequest changeUserNameRequest = new ChangeUserNameRequest();
        changeUserNameRequest.setUserId(2L);
        changeUserNameRequest.setUserName("updateSecondUser");
        String response = userService.changeUserName(changeUserNameRequest);
        assertThat(response).isEqualTo("User name changed");
    }

    @Test
    void resetPassword() {
    }

    @Test
    void count(){
        Long numberOfUser = userService.count();
        assertThat(numberOfUser).isEqualTo(2L);
    }

    @Test
    void deleteUserById() {
        String response = userService.deleteUserById(1L);
        assertThat(response).isEqualTo("User Account Deleted");
        assertThat(userService.count()).isEqualTo(1L);
    }


}