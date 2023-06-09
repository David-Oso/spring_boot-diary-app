package com.example.SpringBootDiaryApp.services.entryService;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import com.example.SpringBootDiaryApp.data.model.Diary;
import com.example.SpringBootDiaryApp.data.model.Entry;
import com.example.SpringBootDiaryApp.data.model.User;
import com.example.SpringBootDiaryApp.data.repositories.EntryRepository;
import com.example.SpringBootDiaryApp.data.repositories.UserRepository;
import com.example.SpringBootDiaryApp.exception.NotFoundException;
import com.example.SpringBootDiaryApp.services.diaryService.DiaryService;
import com.example.SpringBootDiaryApp.services.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.example.SpringBootDiaryApp.utils.AppUtils.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService{
    private final EntryRepository entryRepository;
    private final UserService userService;
    private final DiaryService diaryService;
    @Override
    public String createEntry(CreateEntryRequest createEntryRequest) {
        User foundUser = userService.getUserById(createEntryRequest.getUserId());
        Diary foundDiary = foundUser.getDiary();
       Entry entry = new Entry();
       entry.setTitle(createEntryRequest.getTitle());
       entry.setBody(createEntryRequest.getBody());
       if(createEntryRequest.getBody().length() > 50)
           entry.setDescription(createEntryRequest.getBody().substring(0, 50)+"...");
       entry.setCreatedAt(LocalDateTime.now());
       Entry savedEntry = entryRepository.save(entry);
       foundDiary.getEntries().add(savedEntry);
       diaryService.saveDiary(foundDiary);
        return "Entry added";
    }

    @Override
    public Entry getEntryById(Long entryId) {
        return entryRepository.findById(entryId).orElseThrow(
                ()-> new NotFoundException("Entry not found"));
    }

    @Override
    public String updateEntry(UpdateEntryRequest updateEntryRequest) {
        Entry foundEntry = getEntryById(updateEntryRequest.getEntryId());
        foundEntry.setTitle(updateEntryRequest.getUpdateTitle());
        foundEntry.setBody(updateEntryRequest.getUpdateBody());
        if(updateEntryRequest.getUpdateBody().length() > 50)
            foundEntry.setDescription(updateEntryRequest.getUpdateBody().substring(0, 50)+"...");
        foundEntry.setUpdatedAt(LocalDateTime.now());
        entryRepository.save(foundEntry);
        return "Entry updated";
    }

    @Override
    public Long numberOfEntries() {
        return entryRepository.count();
    }

    @Override
    public Page<Entry> getAllEntries(int pageNumber) {
        if(pageNumber < 1)pageNumber = 0;
        else pageNumber -= 1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return entryRepository.findAll(pageable);
    }

    @Override
    public String deleteEntry( Long entryId) {
        entryRepository.deleteById(entryId);
        return "Entry deleted";
    }
    @Override
    public void deleteAllEntries() {
        entryRepository.deleteAll();
    }

}
