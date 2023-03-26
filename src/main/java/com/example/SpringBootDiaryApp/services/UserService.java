package com.example.SpringBootDiaryApp.services;

import com.example.SpringBootDiaryApp.data.dto.request.LoginRequest;
import com.example.SpringBootDiaryApp.data.dto.request.RegisterUserRequest;
import com.example.SpringBootDiaryApp.data.dto.request.WriteToDiaryRequest;
import com.example.SpringBootDiaryApp.data.dto.response.LoginResponse;
import com.example.SpringBootDiaryApp.data.dto.response.RegisterResponse;
import com.example.SpringBootDiaryApp.data.dto.response.WriteToDiaryResponse;
import com.example.SpringBootDiaryApp.data.model.Entry;
import com.example.SpringBootDiaryApp.data.model.User;

import java.util.Set;

public interface UserService {
    RegisterResponse register(RegisterUserRequest request);
    LoginResponse loginResponse(LoginRequest request);
    User getUserByEmail(String email);
    User getUserById(Long userId);
    WriteToDiaryResponse writeToDiary(WriteToDiaryRequest request);
    Set<Entry> getAllEntries();
    void deleteEntryById(Long entryId);
    void deleteAllEntries();
    void deleteUserById(Long userId);
    void deleteUserByEmail(String email);

}
