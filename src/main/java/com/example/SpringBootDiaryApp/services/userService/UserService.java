package com.example.SpringBootDiaryApp.services.userService;

import com.example.SpringBootDiaryApp.data.dto.request.*;
import com.example.SpringBootDiaryApp.data.dto.response.*;
import com.example.SpringBootDiaryApp.data.model.Entry;
import com.example.SpringBootDiaryApp.data.model.User;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService {
    AuthenticationResponse registerUser(RegisterRequest registerUserRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    User getUserById(Long userId);
    User getUserByEmail(String email);
    String updateUser(UpdateUserRequest updateUserRequest);
    String deleteUserById(Long userId);
    Long count();
}
