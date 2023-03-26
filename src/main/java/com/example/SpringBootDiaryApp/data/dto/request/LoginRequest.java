package com.example.SpringBootDiaryApp.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "field email cannot be left null")
    @NotEmpty(message = "field email cannot be empty")
    @Email()
    private String email;
    @NotNull(message = "field password cannot be null")
    @NotEmpty(message = "field password  cannot be empty")
    @Size(min = 8, max = 20)
    private String password;
}
