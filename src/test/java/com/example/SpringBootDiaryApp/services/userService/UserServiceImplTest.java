package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.RegisterRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateUserRequest;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.exception.ImageUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

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
        registerRequest1.setUserName("user101");
        registerRequest1.setPassword("password");
        registerRequest1.setEmail("email1");

        MockMultipartFile testImageUrl = getMockMultipartFile(DIARY_TEST_IMAGE);
        registerRequest1.setProfileImage(testImageUrl);
        registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName("user102");
        registerRequest2.setPassword("passw");
        registerRequest2.setEmail("email2");
        registerRequest2.setProfileImage(testImageUrl);

        updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserName("user245");
        MockMultipartFile updateImageUrl = getMockMultipartFile(UPDATE_TEST_IMAGE);
        updateUserRequest.setProfileImage(updateImageUrl);

    }

    private static MockMultipartFile getMockMultipartFile(String image) {
        MockMultipartFile testImageUrl;
        try{
        testImageUrl = new MockMultipartFile("test image",
                new FileInputStream(image));
        }catch (IOException exception){
            throw new ImageUploadException(exception.getMessage());
        }
        return testImageUrl;
    }


    @Test
    void registerUser() {
        AuthenticationResponse response = userService.registerUser(registerRequest1);
        AuthenticationResponse response2 = userService.registerUser(registerRequest2);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("User Registration Successful");
        assertThat(response2).isNotNull();
        assertThat(response2.getMessage()).isEqualTo("User Registration Successful");
    }

    @Test
    void authenticate() {
    }

    @Test
    void getUserById() {
        User foundUser = userService.getUserById(1L);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
        assertThat(foundUser.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void getUserByEmail() {
        User foundUser = userService.getUserByEmail("email1");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(registerRequest1.getUserName());
    }

    @Test
    void updateUser() {
        String response = userService.updateUser(1L, updateUserRequest);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("User Details updated");
    }

    @Test
    void countTheNumberOfUsers(){
        Long userCount = userService.count();
        assertThat(userCount).isEqualTo(2);
    }
    @Test
    void deleteUserById(){
        String response = userService.deleteUserById(2L);
        assertThat(response).isEqualTo("User Account Deleted");
        Long userCount = userService.count();
        assertThat(userCount).isEqualTo(1);
    }
}