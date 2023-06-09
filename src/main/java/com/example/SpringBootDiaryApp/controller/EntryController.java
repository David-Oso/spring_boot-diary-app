package com.example.SpringBootDiaryApp.controller;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import com.example.SpringBootDiaryApp.data.model.Entry;
import com.example.SpringBootDiaryApp.services.entryService.EntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/entry/")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    @PostMapping("create")
    public ResponseEntity<?> createEntry(@Valid @RequestBody CreateEntryRequest createEntryRequest) {
        String response = entryService.createEntry(createEntryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("get/{entryId}")
    public ResponseEntity<?> getEntryById(@Valid @PathVariable Long entryId) {
        Entry foundEntry = entryService.getEntryById(entryId);
        return ResponseEntity.status(HttpStatus.OK).body(foundEntry);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEntry(@Valid @RequestBody UpdateEntryRequest updateEntryRequest) {
        String response = entryService.updateEntry(updateEntryRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("all/{page_number}")
    public ResponseEntity<?> getAllEntries(@Valid @PathVariable int page_number) {
        Page<Entry> response = entryService.getAllEntries(page_number);
        return ResponseEntity.ok(response.getContent());
    }

    @DeleteMapping("delete/{entry_id}")
    public ResponseEntity<?> deleteEntryById(@Valid  @PathVariable Long entry_id){
        String response = entryService.deleteEntry(entry_id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity<?> deleteAllEntries(){
        entryService.deleteAllEntries();
        return ResponseEntity.status(HttpStatus.OK).body("All entries deleted");
    }
}