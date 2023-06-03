package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.*;
import com.example.SpringBootDiaryApp.data.dto.response.*;
import com.example.SpringBootDiaryApp.data.model.Entry;
import com.example.SpringBootDiaryApp.data.model.User;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService {
    void registerUser(RegisterRequest registerUserRequest);
    void resendTokenToRegisteredEmail(User user);
    AuthenticationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest, RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    String uploadProfileImage(UploadImageRequest uploadImageRequest);
    User getUserById(Long userId);
    User getUserByEmail(String email);
    String updateUser(UpdateUserRequest updateUserRequest);
    AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    String deleteUserById(Long userId);
    Long count();
}
