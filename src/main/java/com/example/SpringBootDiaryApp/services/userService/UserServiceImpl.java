package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.config.RandomStringGenerator;
import com.example.SpringBootDiaryApp.data.dto.request.*;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.Token;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.data.repositories.DiaryRepository;
import com.example.SpringBootDiaryApp.data.repositories.TokenRepository;
import com.example.SpringBootDiaryApp.data.repositories.UserRepository;
import com.example.SpringBootDiaryApp.exception.NotFoundException;
import com.example.SpringBootDiaryApp.exception.RegistrationException;
import com.example.SpringBootDiaryApp.services.cloud.CloudService;
import com.example.SpringBootDiaryApp.services.emailService.EmailNotificationRequest;
import com.example.SpringBootDiaryApp.services.emailService.EmailService;
import com.example.SpringBootDiaryApp.services.emailService.Recipient;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final CloudService cloudService;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private EmailNotificationRequest emailNotificationRequest;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Override
    public void registerUser(RegisterRequest registerUserRequest) {
        User newUser = new User();
        newUser.setEmail(registerUserRequest.getEmail());
        User savedUser = userRepository.save(newUser);
        sendVerificationMail(savedUser);
    }

    private void sendVerificationMail(User user) {
        emailNotificationRequest = new EmailNotificationRequest();
        Recipient recipient = new Recipient(user.getEmail(), user.getEmail());
        emailNotificationRequest.getTo().add(recipient);
        emailNotificationRequest.setSubject("Activation Of MyDiary Application");

        String otp = RandomStringGenerator.generateRandomString(6);
        emailNotificationRequest.setHtmlContent(
                String.format("""
                        To activate MyDiary Application, enter the verification code below:
                                                    %s                                                 
                        Note: The verification code expires within 5 minutes.
                        """, otp));
        Optional<Token> existingToken = tokenRepository.findTokenByUser(user);
        existingToken.ifPresent(tokenRepository::delete);
        Token token = new Token(user, otp);
        tokenRepository.save(token);
        emailService.sendHtmlMail(emailNotificationRequest);
    }

    @Override
    public AuthenticationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        User verifiesUser = getUserByEmail(emailVerificationRequest.getEmail());
        if (verifiesUser == null)throw new RegistrationException("Invalid email");
        Optional<Token> token = tokenRepository.findTokenByUserAndOtp(verifiesUser, emailVerificationRequest.getVerificationToken());
        if(token.isEmpty())throw new RegistrationException("Invalid token");
        else if(token.get().getExpiryTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(token.get());
            throw new RegistrationException("Token is expired");
        }
        else{
            verifiesUser.setEnabled(true);

        }
        return null;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
//        User foundUser = getUserByEmail(authenticationRequest.getEmail());
//        if(foundUser != null && foundUser.getPassword().equals(authenticationRequest.getPassword()))
//            return AuthenticationResponse.builder()
//                    .isSuccess(true)
//                    .message("Authentication Successful")
//                    .build();
//        else throw new RegistrationException("Authentication failed");
        return null;
    }

    @Override
    public String uploadProfileImage(UploadImageRequest uploadImageRequest) {
        User foundUser = getUserById(uploadImageRequest.getUserId());
        String imageUrl = cloudService.upload(uploadImageRequest.getProfileImage());
        foundUser.setProfileImage(imageUrl);
        userRepository.save(foundUser);
        return "Profile image uploaded";
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if( user == null)
            throw new NotFoundException(String.format("User with email %s not found", email));
        else return user;
    }

    @Override
    public String updateUser(UpdateUserRequest updateUserRequest) {
        return "User Details updated";
    }

    @Override
    public AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return null;
    }

    @Override
    public String deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        return "User Account Deleted";
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    private void validateEmail(String email) {
        User foundUser = userRepository.findUserByEmail(email);
        if(foundUser != null)
            throw new RegistrationException(String.format("User with email %s already exists", email));
    }
}
