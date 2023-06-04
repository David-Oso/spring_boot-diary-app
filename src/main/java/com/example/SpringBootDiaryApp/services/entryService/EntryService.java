package com.example.SpringBootDiaryApp.services.entryService;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import com.example.SpringBootDiaryApp.data.model.Entry;
import org.springframework.data.domain.Page;


public interface EntryService {
    String createEntry(CreateEntryRequest createEntryRequest);
    Entry getEntryById(Long entryId);
    String updateEntry(UpdateEntryRequest updateEntryRequest);
    Long numberOfEntries();
    Page<Entry> getAllEntries(int pageNumber);
    String deleteEntry(Long userId, Long entryId);
    void deleteAllEntries(Long userId);
}
