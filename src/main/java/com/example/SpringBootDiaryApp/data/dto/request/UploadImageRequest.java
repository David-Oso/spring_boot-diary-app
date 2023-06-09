package com.example.SpringBootDiaryApp.data.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.example.SpringBootDiaryApp.utils.AppUtils.IMAGE_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadImageRequest {
    private Long userId;
//    @Pattern(message = "profile image must be a jpeg or jpg or svg format", regexp = IMAGE_REGEX)
    private MultipartFile profileImage;}
