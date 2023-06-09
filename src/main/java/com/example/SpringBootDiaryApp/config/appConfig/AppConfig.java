package com.example.SpringBootDiaryApp.config.appConfig;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SpringBootDiaryApp.config.mailConfig.MailConfig;
import com.example.SpringBootDiaryApp.data.repositories.UserRepository;
import com.example.SpringBootDiaryApp.exception.NotFoundException;
import com.example.SpringBootDiaryApp.services.emailService.EmailNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository userRepository;

    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Value("${mail.api.key}")
    private String mailApiKey;
    @Value("${sendinblue.mail.url}")
    private String mailUrl;

    @Bean
    public MailConfig mailConfig(){
        return new MailConfig(mailApiKey, mailUrl);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name",cloudName,
                        "api_key",apiKey,
                        "api_secret",apiSecret
                )
        );
    }
    @Bean
    public EmailNotificationRequest emailNotificationRequest(){
        return new EmailNotificationRequest();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByName(username)
                .orElseThrow(()-> new NotFoundException(String.format("User with name %s not found", username)));
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
