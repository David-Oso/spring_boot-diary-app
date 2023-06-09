package com.example.SpringBootDiaryApp.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.SpringBootDiaryApp.utils.AppUtils.EMAIL_REGEX_STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailVerificationRequest {

    @NotEmpty(message = "field email cannot be empty")
    @NotBlank(message = "field email cannot be blank")
    @Email(message = "must be valid email address", regexp = EMAIL_REGEX_STRING)
    private String email;


}
