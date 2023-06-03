package com.example.SpringBootDiaryApp.controller;

import com.example.SpringBootDiaryApp.data.dto.request.*;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.services.userService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest){
        userService.registerUser(emailVerificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Check your mail for the verification token to activate your diary");
    }

    @PostMapping("resend_token")
    public ResponseEntity<?> resendVerificationToken(@Valid @RequestParam String email){
        userService.resendTokenToRegisteredEmail(email);
        return ResponseEntity.status(HttpStatus.CREATED).body("Check your mail for the verification token to activate your diary");
    }
    @PostMapping("verify")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody RegisterRequest registerRequest){
        AuthenticationResponse response = userService.verifyEmail(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse response = userService.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping(value="upload_profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(@Valid @ModelAttribute UploadImageRequest uploadImageRequest){
        String response = userService.uploadProfileImage(uploadImageRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<?> getUserById(@Valid @PathVariable Long id){
        User foundUser = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }
    @GetMapping("get/email")
    public ResponseEntity<?> getUserByEmail(@Valid @RequestParam String email){
        User foundUser = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }
    @PostMapping("change_user_name")
    public ResponseEntity<?> changeUserName(@Valid @RequestBody ChangeUserNameRequest changeUserNameRequest){
        String response = userService.changeUserName(changeUserNameRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("reset_password_mail")
    public ResponseEntity<?> sendResetPasswordMail(@Valid @RequestParam String email){
        String response = userService.sendRestPasswordMail(email);
        return ResponseEntity.ok(response);
    }
    @PostMapping("reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        AuthenticationResponse response = userService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable Long id){
        String response = userService.deleteUserById(id);
        return ResponseEntity.ok(response);
    }
}
