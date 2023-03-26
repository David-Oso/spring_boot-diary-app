package com.example.SpringBootDiaryApp.services;

import com.example.SpringBootDiaryApp.cloud.CloudService;
import com.example.SpringBootDiaryApp.data.dto.request.LoginRequest;
import com.example.SpringBootDiaryApp.data.dto.request.RegisterUserRequest;
import com.example.SpringBootDiaryApp.data.dto.request.WriteToDiaryRequest;
import com.example.SpringBootDiaryApp.data.dto.response.LoginResponse;
import com.example.SpringBootDiaryApp.data.dto.response.RegisterResponse;
import com.example.SpringBootDiaryApp.data.dto.response.WriteToDiaryResponse;
import com.example.SpringBootDiaryApp.data.model.Diary;
import com.example.SpringBootDiaryApp.data.model.Entry;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.data.repositories.DiaryRepository;
import com.example.SpringBootDiaryApp.data.repositories.EntryRepository;
import com.example.SpringBootDiaryApp.data.repositories.UserRepository;
import com.example.SpringBootDiaryApp.exception.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final EntryRepository entryRepository;
    private final DiaryRepository diaryRepository;
    private final CloudService cloudService;
    private final ModelMapper modelMapper;
    @Override
    public RegisterResponse register(RegisterUserRequest request) {
        validateEmail(request);
        validateAge(request.getAge());
        User user = modelMapper.map(request, User.class);
        var imageUrl = cloudService.upload(request.getUserImage());
        if(imageUrl == null)
            throw new ImageUploadException("Driver Registration failed ...");
        user.setUserImage(imageUrl);
        user.setCreatedAt(LocalDateTime.now());

        User saveduser = userRepository.save(user);
        return RegisterResponse.builder()
                .isRegistered(true)
                .id(saveduser.getId())
                .message("User Registration successful ")
                .build();
    }

    private void validateEmail(RegisterUserRequest request) {
        User findUser = getUserByEmail(request.getEmail());
        if(findUser != null) throw new UserAlreadyExistsException(
                String.format("User with email %s already exists", request.getEmail()));
        if(request.getEmail() == null || request.getEmail().length() == 0){
            String userName = request.getUserName();
            request.setEmail(generateEmail(userName));
        }
    }

    private String generateEmail(String userName) {
        SecureRandom randomNumbers = new SecureRandom();
        String numbers = randomNumbers.ints(3, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        return userName.concat(numbers.concat("@gmail.com"));
    }

    private void validateAge(int age) {
        if(age < 6 || age > 100)
            throw new BusinessLogicException("Diary is only meant for age 6 to 100");
    }


    @Override
    public LoginResponse loginResponse(LoginRequest request) {
        User user = getUserByEmail(request.getEmail());
        if(!(user.getPassword().equals(request.getPassword())))
                throw new InvalidDetailException("Incorrect password !!!");
        else return LoginResponse.builder()
                .id(user.getId())
                .isUnlocked(true)
                .message("Diary is unlocked")
                .build();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->
                new EmailNotFoundException(
                        String.format("User with email %s not found ...", email)));

    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(
                        String.format("user with id %s not found ...", userId)));
    }

    @Override
    public WriteToDiaryResponse writeToDiary(WriteToDiaryRequest request) {
        User user = getUserById(request.getUserId());
        Diary diary = new Diary();
        Entry entry = new Entry();
        entry.setTitle(request.getTitle());
        entry.setBody(request.getBody());
        entry.setDescription(request.getBody().substring(0, 50));
        entry.setCreatedAt(LocalDateTime.now());

        Entry savedEntry = entryRepository.save(entry);
        diary.setEntries(Set.of(savedEntry));

        Diary savedDiary = diaryRepository.save(diary);
        user.setDiary(savedDiary);
        return WriteToDiaryResponse.builder()
                .id(savedEntry.getId())
                .message("Entry added successfully")
                .build();
    }

    @Override
    public Set<Entry> getAllEntries() {
        List<Entry> entries = entryRepository.findAll();
        return new HashSet<>(entries);
    }

    @Override
    public void deleteEntryById(Long entryId) {

    }

    @Override
    public void deleteAllEntries() {

    }

    @Override
    public void deleteUserById(Long userId) {

    }

    @Override
    public void deleteUserByEmail(String email) {

    }
}
