package com.example.SpringBootDiaryApp.data.dto.request;

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
public class WriteToDiaryRequest {
    private Long userId;
    @Size(min = 5, max = 50)
    @NotNull(message = "field title cannot be null")
    @NotEmpty(message = "field title cannot be empty")
    private String title;
    @NotNull(message = "field body cannot be null")
    @NotEmpty(message = "field body cannot be empty")
    private String body;
}
