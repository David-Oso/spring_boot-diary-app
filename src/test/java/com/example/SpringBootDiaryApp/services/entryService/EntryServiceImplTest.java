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
    private CreateEntryRequest entryRequest;
    private CreateEntryRequest entryRequest1;
    private UpdateEntryRequest updateEntryRequest;


    @BeforeEach
    void setUp() {
        entryRequest = new CreateEntryRequest();
        entryRequest.setTitle("My journey to semicolon, which is a great day");
        entryRequest.setBody("My coming to semicolon was a life changing event for me cos i have not left akure or really travelled to other states since i was a kid");

        entryRequest1 = new CreateEntryRequest();
        entryRequest1.setTitle("My journey to semicolon, which is a great day");
        entryRequest1.setBody("My coming to semicolon was a life changing event for me cos i have not left akure or really travelled to other states since i was a kid");

        updateEntryRequest = new UpdateEntryRequest();
        updateEntryRequest.setUpdateTitle("How i will be collection dollars in some months time");
        updateEntryRequest.setUpdateBody("As you dey see me so, i no dey follow you collect Nigerian Naira. Na European pounds me i wan dey collect");
    }

    @Test
    void createEntry() {

        String response = entryService.createEntry(entryRequest);
        String response1 = entryService.createEntry(entryRequest1);
        assertThat(response).isEqualTo("Entry added");
        assertThat(response1).isEqualTo("Entry added");
    }

    @Test
    void getEntryById() {
        Entry foundEntry = entryService.getEntryById(1L);
        assertThat(foundEntry.getBody()).isEqualTo(entryRequest.getBody());
    }

    @Test
    void updateEntry() {
        updateEntryRequest.setEntryId(1L);
        String response = entryService.updateEntry(updateEntryRequest);
        assertThat(response).isEqualTo("Entry updated");
    }

    @Test
    void deleteEntry() {
        String response = entryService.deleteEntry(2L);
        assertThat(response).isEqualTo("Entry deleted");
    }
}