package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.*;
import com.example.SpringBootDiaryApp.data.dto.response.*;
import com.example.SpringBootDiaryApp.data.model.User;


public interface UserService {
    void registerUser(EmailVerificationRequest emailVerificationRequest);
    void resendTokenToRegisteredEmail(String email);
    AuthenticationResponse verifyEmail(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    String uploadProfileImage(UploadImageRequest uploadImageRequest);
    User getUserById(Long userId);
    User getUserByEmail(String email);
    String changeUserName(ChangeUserNameRequest changeUserNameRequest);
    String sendRestPasswordMail(String email);
    AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    String deleteUserById(Long userId);
    Long count();
}
