package com.example.SpringBootDiaryApp.data.repositories;

import com.example.SpringBootDiaryApp.data.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
