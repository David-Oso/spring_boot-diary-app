package com.example.SpringBootDiaryApp.data.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.example.SpringBootDiaryApp.utils.AppUtils.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {

    @NotBlank(message = "field user name cannot be blank")
    @NotEmpty(message = "field user name cannot be empty")
    @Pattern(message = "user name contains letter and numbers e.g user101", regexp = USER_NAME_REGEX)
    private String userName;

    @NotEmpty(message = "field email cannot be empty")
    @NotBlank(message = "field email cannot be blank")
    @Email(message = "must be valid email address", regexp = EMAIL_REGEX_STRING)
    private String email;


    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = PASSWORD_REGEX_STRING,
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
}
