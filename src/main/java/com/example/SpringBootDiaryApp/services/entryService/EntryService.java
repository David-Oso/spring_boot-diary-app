package com.example.SpringBootDiaryApp.services.entryService;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import com.example.SpringBootDiaryApp.data.model.Entry;

public interface EntryService {
    String createEntry(CreateEntryRequest createEntryRequest);
    Entry getEntryById(Long entryId);
    String updateEntry(UpdateEntryRequest updateEntryRequest);
    String deleteEntry(Long entryId);
}
