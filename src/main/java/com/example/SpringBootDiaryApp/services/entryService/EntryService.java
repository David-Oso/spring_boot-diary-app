package com.example.SpringBootDiaryApp.services.entryService;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import com.example.SpringBootDiaryApp.data.model.Entry;

import java.util.List;

public interface EntryService {
    String createEntry(CreateEntryRequest createEntryRequest);
    Entry getEntryById(Long entryId);
    String updateEntry(UpdateEntryRequest updateEntryRequest);
    Long numberOfEntries();
    String deleteEntry(Long entryId);
    List<Entry> getAllEntries();
    void deleteAllEntries();
}
