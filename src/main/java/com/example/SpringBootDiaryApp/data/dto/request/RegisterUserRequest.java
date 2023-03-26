package com.example.SpringBootDiaryApp.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {
    @NotNull(message = "field user name cannot be null")
    @NotEmpty(message = "field user name cannot be empty")
    private String userName;
    @NotNull(message = "field age cannot be null")
    @NotEmpty(message = "field age cannot be empty")
    @Size(min = 5, max = 100)
    private int age;
    @NotNull(message = "field email cannot be null")
    @NotEmpty(message = "field email cannot be empty")
    @Email(message = "must be a valid email address")
    private String email;
    @NotNull(message = "field password cannot be null")
    @NotEmpty(message = "field password cannot be empty")
    private String password;
    @NotNull(message = "field image cannot be null")
    @NotEmpty(message = "Input an image")
    private MultipartFile userImage;

}
