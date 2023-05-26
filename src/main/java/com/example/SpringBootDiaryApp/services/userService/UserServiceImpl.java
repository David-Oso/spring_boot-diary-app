package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.AuthenticationRequest;
import com.example.SpringBootDiaryApp.data.dto.request.RegisterRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateUserRequest;
import com.example.SpringBootDiaryApp.data.dto.response.AuthenticationResponse;
import com.example.SpringBootDiaryApp.data.model.Diary;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.data.repositories.DiaryRepository;
import com.example.SpringBootDiaryApp.data.repositories.UserRepository;
import com.example.SpringBootDiaryApp.exception.ImageUploadException;
import com.example.SpringBootDiaryApp.exception.NotFoundException;
import com.example.SpringBootDiaryApp.services.cloud.CloudService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final CloudService cloudService;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public AuthenticationResponse registerUser(RegisterRequest registerUserRequest) {
        User user = modelMapper.map(registerUserRequest, User.class);
        user.setRegistered(true);
        Diary createdDiary = diaryRepository.save(new Diary());
        user.setDiary(createdDiary);
        String imageUrl = uploadImage(registerUserRequest.getProfileImage());
        user.setProfileImage(imageUrl);
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .isSuccess(true)
                .message("User Registration Successful")
                .build();
    }

    private String uploadImage(MultipartFile profileImage) {
        String imageUrl = cloudService.upload(profileImage);
        if(imageUrl == null)
            throw new ImageUploadException("You must upload your profile image");
        else return imageUrl;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return null;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(
                ()-> new NotFoundException(String.format("User with email %s doesn't exist", email)));
    }

    @Override
    public String updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User foundUser = getUserById(userId);
        foundUser.setUserName(updateUserRequest.getUserName());
        String updateImageUrl = uploadImage(updateUserRequest.getProfileImage());
        foundUser.setProfileImage(updateImageUrl);
        foundUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(foundUser);
        return "User Details updated";
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
}
