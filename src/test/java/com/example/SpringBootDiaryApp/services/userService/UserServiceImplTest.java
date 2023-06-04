package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.*;
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
    @Autowired UserService userService;
    private EmailVerificationRequest emailVerificationRequest1;
    private EmailVerificationRequest emailVerificationRequest2;
    private RegisterRequest registerRequest1;
    private RegisterRequest registerRequest2;
    private AuthenticationRequest authenticationRequest1;
    private AuthenticationRequest authenticationRequest2;
    private UploadImageRequest uploadImageRequest1;
    private UploadImageRequest uploadImageRequest2;
    private ChangeUserNameRequest changeUserNameRequest1;
    private ChangeUserNameRequest changeUserNameRequest2;
    private ResetPasswordRequest resetPasswordRequest;
    @BeforeEach
    void setUp() {
        emailVerificationRequest1 = new EmailVerificationRequest();
        emailVerificationRequest1.setEmail("segifif866@rockdian.com");

        emailVerificationRequest2 = new EmailVerificationRequest();
        emailVerificationRequest2.setEmail("demoko3775@vaband.com");

        registerRequest1 = new RegisterRequest();
        registerRequest1.setUserName("firstUser");
        registerRequest1.setEmail("segifif866@rockdian.com");
        registerRequest1.setVerificationToken("PbbvuGtK");
        registerRequest1.setPassword("Password111$");

        registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("secondUser");
        registerRequest2.setEmail("demoko3775@vaband.com");
        registerRequest2.setVerificationToken("_onF2kH8");
        registerRequest2.setPassword("Password222@");

        authenticationRequest1 = new AuthenticationRequest();
        authenticationRequest1.setUserName("firstUser");
        authenticationRequest1.setPassword("Password111$");

        authenticationRequest2 = new AuthenticationRequest();
        authenticationRequest2.setUserName("secondUser");
        authenticationRequest2.setPassword("Password222@");

        uploadImageRequest1 = new UploadImageRequest();
        uploadImageRequest1.setUserId(1L);
        uploadImageRequest1.setProfileImage(uploadTestImageFile(DIARY_TEST_IMAGE));

        uploadImageRequest2 = new UploadImageRequest();
        uploadImageRequest2.setUserId(2L);
        uploadImageRequest2.setProfileImage(uploadTestImageFile(UPDATE_TEST_IMAGE));

        changeUserNameRequest1 = new ChangeUserNameRequest();
        changeUserNameRequest1.setUserId(1L);
        changeUserNameRequest1.setUserName("updateFirstUserName");

        changeUserNameRequest2 = new ChangeUserNameRequest();
        changeUserNameRequest2.setUserId(2L);
        changeUserNameRequest2.setUserName("updateSecondUserName");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("segifif866@rockdian.com");
        resetPasswordRequest.setResetPasswordToken("o1Z4ApAe");
        resetPasswordRequest.setNewPassword("NewPassword111&");
    }

    private MultipartFile uploadTestImageFile(String imageUrl){
        try{
            return new MockMultipartFile("test_upload_image",
                    new FileInputStream(imageUrl));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Test
    void registerUser() {
        userService.registerUser(emailVerificationRequest1);
        userService.registerUser(emailVerificationRequest2);
    }

    @Test
    void resendTokenToRegisteredEmail() {
        userService.resendTokenToRegisteredEmail("segifif866@rockdian.com");
        userService.resendTokenToRegisteredEmail("demoko3775@vaband.com");
    }

    @Test
    void verifyEmail() {
        AuthenticationResponse response1 = userService.verifyEmail(registerRequest1);
        assertThat(response1.getMessage()).isEqualTo("Authentication verification successful");
        assertThat(response1.isSuccess()).isEqualTo(true);

        AuthenticationResponse response2 = userService.verifyEmail(registerRequest2);
        assertThat(response2.getMessage()).isEqualTo("Authentication verification successful");
        assertThat(response2.isSuccess()).isEqualTo(true);
    }

    @Test
    void authenticate() {
        AuthenticationResponse response1 = userService.authenticate(authenticationRequest1);
        assertThat(response1.getMessage()).isEqualTo("Authentication successful");
        assertThat(response1.isSuccess()).isEqualTo(true);

        AuthenticationResponse response2 = userService.authenticate(authenticationRequest2);
        assertThat(response2.getMessage()).isEqualTo("Authentication successful");
        assertThat(response2.isSuccess()).isEqualTo(true);
    }

    @Test
    void uploadProfileImage() {
        String uploadImageResponse1 = userService.uploadProfileImage(uploadImageRequest1);
        assertThat(uploadImageResponse1).isEqualTo("Profile image uploaded");
        String uploadImageResponse2 = userService.uploadProfileImage(uploadImageRequest2);
        assertThat(uploadImageResponse2).isEqualTo("Profile image uploaded");
    }

    @Test
    void getUserById() {
        User foundUser1 = userService.getUserById(1L);
        assertThat(foundUser1.getName()).isEqualTo(registerRequest1.getUserName());
        assertThat(foundUser1.getEmail()).isEqualTo(registerRequest1.getEmail());

        User foundUser2 = userService.getUserById(2L);
        assertThat(foundUser2.getName()).isEqualTo(registerRequest2.getUserName());
        assertThat(foundUser2.getEmail()).isEqualTo(registerRequest2.getEmail());
    }

    @Test
    void getUserByEmail() {
        User foundUser1 = userService.getUserByEmail("segifif866@rockdian.com");
        assertThat(foundUser1.getName()).isEqualTo(registerRequest1.getUserName());
        User foundUser2 = userService.getUserByEmail("demoko3775@vaband.com");
        assertThat(foundUser2.getName()).isEqualTo(registerRequest2.getUserName());
    }

    @Test
    void changeUserName() {
        String response1 = userService.changeUserName(changeUserNameRequest1);
        assertThat(response1).isEqualTo("User name changed");
        String response2 = userService.changeUserName(changeUserNameRequest2);
        assertThat(response2).isEqualTo("User name changed");
    }

    @Test
    void sendRestPasswordMail() {
        userService.sendRestPasswordMail("segifif866@rockdian.com");
    }

    @Test
    void resetPassword() {
        AuthenticationResponse response = userService.resetPassword(resetPasswordRequest);
        assertThat(response.getMessage()).isEqualTo("Password changed successfully");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void deleteUserById(){
        assertThat(userService.count()).isEqualTo(2L);
        String response = userService.deleteUserById(2L);
        assertThat(response).isEqualTo("User Account Deleted");
        assertThat(userService.count()).isEqualTo(1L);
    }
}