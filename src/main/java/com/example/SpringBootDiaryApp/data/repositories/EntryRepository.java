package com.example.SpringBootDiaryApp.data.repositories;

import com.example.SpringBootDiaryApp.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
