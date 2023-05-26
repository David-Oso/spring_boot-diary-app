package com.example.SpringBootDiaryApp.services.diaryService;

import com.example.SpringBootDiaryApp.data.model.Diary;
import com.example.SpringBootDiaryApp.data.repositories.DiaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;
    @Override
    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }
}
