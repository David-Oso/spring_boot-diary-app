package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.config.RandomStringGenerator;
import com.example.SpringBootDiaryApp.config.security.JwtService;
import com.example.SpringBootDiaryApp.config.security.jwtToken.JwtToken;
import com.example.SpringBootDiaryApp.config.security.jwtToken.JwtTokenRepository;
import com.example.SpringBootDiaryApp.data.dto.request.*;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.Diary;
import com.example.SpringBootDiaryApp.data.model.Token;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.data.repositories.TokenRepository;
import com.example.SpringBootDiaryApp.data.repositories.UserRepository;
import com.example.SpringBootDiaryApp.exception.NotFoundException;
import com.example.SpringBootDiaryApp.exception.RegistrationException;
import com.example.SpringBootDiaryApp.services.cloud.CloudService;
import com.example.SpringBootDiaryApp.services.emailService.EmailNotificationRequest;
import com.example.SpringBootDiaryApp.services.emailService.EmailService;
import com.example.SpringBootDiaryApp.services.emailService.Recipient;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final CloudService cloudService;
    private final UserRepository userRepository;
    private EmailNotificationRequest notificationRequest;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final JwtTokenRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerUser(EmailVerificationRequest emailVerificationRequest) {
        User newUser = new User();
        newUser.setEmail(emailVerificationRequest.getEmail());
        User existingUser = userRepository.findUserByEmail(emailVerificationRequest.getEmail());
        if(existingUser != null)
            resendTokenToRegisteredEmail(emailVerificationRequest.getEmail());
        else{
            User savedUser = userRepository.save(newUser);
            sendVerificationMail(savedUser);
        }
    }
    @Override
    public void resendTokenToRegisteredEmail(String email) {
        User existingUser = getUserByEmail(email);
        if (existingUser != null && !existingUser.isVerified())
            sendVerificationMail(existingUser);
//        Email already registered. Verification Token has been sent to your email verify your account
    }

    private void sendVerificationMail(User user) {
        notificationRequest = new EmailNotificationRequest();
        Recipient recipient = new Recipient(user.getEmail(), user.getEmail());
        notificationRequest.getTo().add(recipient);
        notificationRequest.setSubject("Activation Of MyDiary Application");

        String otp = RandomStringGenerator.generateRandomString(6);
        String htmlContent = getEmailMessage(otp);
        notificationRequest.setHtmlContent(htmlContent);
        Optional<Token> existingToken = tokenRepository.findTokenByUser(user);
        existingToken.ifPresent(tokenRepository::delete);
        Token token = new Token(user, otp);
        tokenRepository.save(token);
        emailService.sendHtmlMail(notificationRequest);
    }

    private String getEmailMessage(String otp) {
        return String.format("""
                     To activate MyDiary Application, enter the verification code below:
                     
                                                    %s  \s
                                                                                                 \s
                     Note: The verification code expires within 5 minutes.
                     
                       """, otp);
    }

    @Override
    public AuthenticationResponse verifyEmail(RegisterRequest registerRequest) {
        User verifiesUser = getUserByEmail(registerRequest.getEmail());
        if (verifiesUser == null)throw new RegistrationException("Invalid email");
        Optional<Token> token = tokenRepository.findTokenByUserAndOtp(verifiesUser, registerRequest.getVerificationToken());
        if(token.isEmpty())throw new RegistrationException("Invalid token");
        else if(token.get().getExpiryTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(token.get());
            throw new RegistrationException("Token is expired");
        }
        else{
            User saveUser = saveVerifiedUser(verifiesUser, registerRequest.getUserName(), registerRequest.getPassword());
            tokenRepository.delete(token.get());
            String jwtAccessToken = jwtService.generateAccessToken(saveUser);
            String jwtRefreshToken = jwtService.generateRefreshToken(saveUser);
            deleteExpiredOrRevokedJwtToken();
            saveUserJwtToken(saveUser, jwtAccessToken);
            return AuthenticationResponse.builder()
                    .isSuccess(true)
                    .message("Account verification successful")
                    .accessToken(jwtAccessToken)
                    .refreshToken(jwtRefreshToken)
                    .build();
        }
    }

    private User saveVerifiedUser(User verifiesUser, String userName, String password) {
        verifiesUser.setName(userName);
        verifiesUser.setPassword(passwordEncoder.encode(password));
        verifiesUser.setVerified(true);
        verifiesUser.setDiary(new Diary());
        User savedUser = userRepository.save(verifiesUser);
        sendSuccessfulRegistrationMail(savedUser);
        return savedUser;
    }

    private void sendSuccessfulRegistrationMail(User user) {
        notificationRequest = new EmailNotificationRequest();
        notificationRequest.setSubject("Successful Account Creation");
        Recipient recipient = new Recipient(user.getName(), user.getEmail());
        notificationRequest.getTo().add(recipient);
        notificationRequest.setHtmlContent(String.format("""
                Dear %s, you have Diary has been verified.
                Kindly enjoy and judiciously use your diary wisely.
                
                Thank you for choosing MyDiary
                
                """, user.getName()));
        emailService.sendHtmlMail(notificationRequest);
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUserName(),
                        authenticationRequest.getPassword()
                ));
        User user = userRepository.findByName(authenticationRequest.getUserName())
                .orElseThrow(()-> new NotFoundException(String.format("User with name %s not found", authenticationRequest.getUserName())));
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        deleteExpiredOrRevokedJwtToken();
        saveUserJwtToken(user, accessToken);
        return AuthenticationResponse.builder()
                .isSuccess(true)
                .message("Authentication successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String uploadProfileImage(UploadImageRequest uploadImageRequest) {
        User foundUser = getUserById(uploadImageRequest.getUserId());
        String imageUrl = cloudService.upload(uploadImageRequest.getProfileImage());
        foundUser.setProfileImage(imageUrl);
        foundUser.setUpdatedAt(LocalDateTime.now());
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
    public String changeUserName(ChangeUserNameRequest changeUserNameRequest) {
        User user = getUserById(changeUserNameRequest.getUserId());
        user.setName(changeUserNameRequest.getUserName());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User name changed";
    }

    @Override
    public String sendRestPasswordMail(String email) {
        User foundUser = getUserByEmail(email);
        notificationRequest = new EmailNotificationRequest();
        Recipient recipient = new Recipient(foundUser.getName(), foundUser.getEmail());
        notificationRequest.getTo().add(recipient);
        notificationRequest.setSubject("Change password");

        String otp = RandomStringGenerator.generateRandomString(6);
        String htmlContent = changePasswordMessage(foundUser.getName(), otp);
        notificationRequest.setHtmlContent(htmlContent);
        Optional<Token> existingToken = tokenRepository.findTokenByUser(foundUser);
        existingToken.ifPresent(tokenRepository::delete);
        Token token = new Token(foundUser, otp);
        tokenRepository.save(token);
        emailService.sendHtmlMail(notificationRequest);
        return "Check you mail to process your request";
    }


    private String changePasswordMessage(String userName, String otp) {
        return String.format("""
                Dear %s, it is a best thing to do when you suspect that your information is not being private again
                To change your password kindly enter the following code: \n
                                                %s
                MyDiary serves you well.
                """, userName, otp);
    }

    @Override
    public AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User verifiedUser = getUserByEmail(resetPasswordRequest.getEmail());
        if(verifiedUser == null)throw new RuntimeException("Invalid email");
        Optional<Token> token = tokenRepository.findTokenByUserAndOtp(verifiedUser, resetPasswordRequest.getResetPasswordToken());
        if(token.isEmpty())throw new RuntimeException("Token is invalid");
        else if(token.get().getExpiryTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(token.get());
            throw new RuntimeException("Token is expired");
        }
        else {
            verifiedUser.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            User savedUser = userRepository.save(verifiedUser);
            tokenRepository.delete(token.get());
            String accessToken = jwtService.generateAccessToken(savedUser);
            String refreshToken = jwtService.generateRefreshToken(savedUser);
            revokeAllUserTokens(verifiedUser);
            saveUserJwtToken(savedUser, accessToken);
            deleteExpiredOrRevokedJwtToken();

            return AuthenticationResponse.builder()
                .isSuccess(true)
                .message("Password changed successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }
    }
    @Override
    public String deleteUserById(Long userId) {
        User foundUser = getUserById(userId);
        revokeAllUserTokens(foundUser);
//        var foundJwtTokens = jwtTokenRepository.findAllValidTokenByUser(userId);
//        jwtTokenRepository.deleteAll(foundJwtTokens);
        deleteExpiredOrRevokedJwtToken();
        userRepository.deleteById(userId);
        userRepository.delete(foundUser);
        return "User Account Deleted";
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    private void saveUserJwtToken(User user, String jwtToken){
        JwtToken token = JwtToken.builder()
                .user(user)
                .jwtToken(jwtToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = jwtTokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(jwtToken -> {
            jwtToken.setExpired(true);
            jwtToken.setRevoked(true);
        });
        deleteExpiredOrRevokedJwtToken();
        jwtTokenRepository.saveAll(validUserTokens);
    }

    private void deleteExpiredOrRevokedJwtToken(){
        for(JwtToken jwt : jwtTokenRepository.findAll()){
            if(jwt.isExpired() || jwt.isRevoked())
                jwtTokenRepository.delete(jwt);
        }
    }



//    public void refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException{
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userName;
//        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userName = jwtService.extractUsername(refreshToken);
//        if(userName != null){
//            User user = this.userRepository.findByName(userName).orElseThrow();
//            if(jwtService.isTokenValid(refreshToken, user)){
//                String accessToken = jwtService.generateAccessToken(user);
//                revokeAllUserTokens(user);
//                saveUserJwtToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }
}
