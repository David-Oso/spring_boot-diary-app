package com.example.SpringBootDiaryApp.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.SpringBootDiaryApp.utils.AppUtils.PASSWORD_REGEX_STRING;
import static com.example.SpringBootDiaryApp.utils.AppUtils.USER_NAME_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    @NotBlank(message = "field user name cannot be blank")
    @NotEmpty(message = "field user name cannot be empty")
    @Pattern(message = "user name contains letter and numbers e.g user101", regexp = USER_NAME_REGEX)
    private String userName;

    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = PASSWORD_REGEX_STRING,
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
}
