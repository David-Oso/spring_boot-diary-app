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
    public ResponseEntity<?> createEntry(@Valid @RequestBody CreateEntryRequest createEntryRequest){
        String response = entryService.createEntry(createEntryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("get/{entryId}")
    public ResponseEntity<?> getEntryById(@Valid @PathVariable Long entryId){
        Entry foundEntry = entryService.getEntryById(entryId);
        return ResponseEntity.status(HttpStatus.OK).body(foundEntry);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEntry(@Valid @RequestBody UpdateEntryRequest updateEntryRequest){
        String response = entryService.updateEntry(updateEntryRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("all/{page_number}")
    public ResponseEntity<?> getAllEntries(@Valid @PathVariable int page_number){
        Page<Entry> response = entryService.getAllEntries(page_number);
        return ResponseEntity.ok(response.getContent());
    }

//    @DeleteMapping("delete/{userId, entryId}")
//    public ResponseEntity<?> deleteEntry(@Valid @PathVariable Long userId, Long entryId){
//        String response = entryService.deleteEntry(userId, entryId);
//        return ResponseEntity.ok(response);
//    }

    @DeleteMapping("all/delete/{userId}")
    public ResponseEntity<?> deleteAll(@Valid @PathVariable Long userId){
        entryService.deleteAllEntries(userId);
        return ResponseEntity.ok("Entries are deleted");
    }
}
//    String deleteEntry(Long userId, Long entryId);
//    void deleteAllEntries(Long userId);