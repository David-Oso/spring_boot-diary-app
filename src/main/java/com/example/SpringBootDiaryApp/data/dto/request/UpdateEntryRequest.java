package com.example.SpringBootDiaryApp.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEntryRequest {
    private Long userId;
    private Long entryId;
    private String updateTitle;
    private String updateBody;
}
