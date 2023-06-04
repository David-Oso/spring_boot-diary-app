package com.example.SpringBootDiaryApp.services.entryService;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import com.example.SpringBootDiaryApp.data.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class EntryServiceImplTest {
    @Autowired EntryService entryService;
    private CreateEntryRequest createEntryRequest;
    private CreateEntryRequest createEntryRequest1;
    private CreateEntryRequest createEntryRequest2;
    private UpdateEntryRequest updateEntryRequest;

    @BeforeEach
    void setUp() {
        createEntryRequest = new CreateEntryRequest();
        createEntryRequest.setUserId(1L);
        createEntryRequest.setTitle("My first entry in MyDiary application");
        createEntryRequest.setBody("This is my first entry in MyDiary application. As i dey like this, I never chop since morning because i just dey code and code away");

        createEntryRequest1 = new CreateEntryRequest();
        createEntryRequest1.setUserId(1L);
        createEntryRequest1.setTitle("My second entry in MyDiary application");
        createEntryRequest1.setBody("This is my second entry in MyDiary application. As i dey like this, I never chop since morning because i just dey code and code away");

        createEntryRequest2 = new CreateEntryRequest();
        createEntryRequest2.setUserId(1L);
        createEntryRequest2.setTitle("My third entry in MyDiary application");
        createEntryRequest2.setBody("This is my third entry in MyDiary application. As i dey like this, I never chop since morning because i just dey code and code away");

        updateEntryRequest = new UpdateEntryRequest();
        updateEntryRequest.setEntryId(1L);
        updateEntryRequest.setUpdateTitle("Updating my first entry in MyDiary application");
        updateEntryRequest.setUpdateBody("This is me updating the first entry in MyDiary application. ... It was a wonderful event at last");
    }

    @Test
    void createEntry() {
//        String createEntryResponse = entryService.createEntry(createEntryRequest);
//        assertThat(createEntryResponse).isEqualTo("Entry added");

        String createEntryResponse1 = entryService.createEntry(createEntryRequest1);
        assertThat(createEntryResponse1).isEqualTo("Entry added");
    }

    @Test
    void getEntryById() {
        Entry foundEntry = entryService.getEntryById(1L);
        assertThat(foundEntry.getTitle()).isEqualTo(createEntryRequest.getTitle());
    }

    @Test
    void updateEntry() {
        String response = entryService.updateEntry(updateEntryRequest);
        assertThat(response).isEqualTo("Entry updated");
    }

    @Test
    void getAllEntries() {
        System.out.println(entryService.getAllEntries(1));
        assertThat(entryService.numberOfEntries()).isEqualTo(2);
    }

    @Test
    void deleteEntry() {
        assertThat(entryService.numberOfEntries()).isEqualTo(4L);
        String response = entryService.deleteEntry(1L,2L);
        assertThat(entryService.numberOfEntries()).isEqualTo(3L);
        assertThat(response).isEqualTo("Entry deleted");
    }

    @Test
    void deleteAllEntries() {
//        entryService.createEntry(createEntryRequest2);
        assertThat(entryService.numberOfEntries()).isEqualTo(4L);
        entryService.deleteAllEntries(1L);
        assertThat(entryService.numberOfEntries()).isEqualTo(0L);
    }
}