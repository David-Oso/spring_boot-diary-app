package com.example.SpringBootDiaryApp.services.entryService;

import com.example.SpringBootDiaryApp.data.dto.request.CreateEntryRequest;
import com.example.SpringBootDiaryApp.data.dto.request.UpdateEntryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class EntryServiceImplTest {
    @Autowired EntryService entryService;
    private CreateEntryRequest createEntryRequest1;
    private CreateEntryRequest createEntryRequest2;
    private UpdateEntryRequest updateEntryRequest;


    @BeforeEach
    void setUp() {
        createEntryRequest1 = new CreateEntryRequest();
        createEntryRequest1.setUserId(1L);
        createEntryRequest1.setTitle("The day i came to semicolon");
        createEntryRequest1.setBody("The day i came to semicolon was a very funny one cos i was just looking like lucosade boost. The first day was also fun because we played");

        createEntryRequest2 = new CreateEntryRequest();
        createEntryRequest2.setUserId(1L);
        createEntryRequest2.setTitle("The day i came to semicolon");
        createEntryRequest2.setBody("The day i came to semicolon was a very funny one cos i was just looking like lucosade boost. The first day was also fun because we played");

        updateEntryRequest = new UpdateEntryRequest();
        updateEntryRequest.setEntryId(2L);
        updateEntryRequest.setUpdateTitle("We are in our 11 month");
        updateEntryRequest.setUpdateBody("We are in our 11 month already and we are almost done with the program although we will not be taught kotlin but i think we can do that ourselves");
    }

    @Test
    void createEntry() {
        String response1 = entryService.createEntry(createEntryRequest1);
        assertThat(response1).isNotNull();
        assertThat(response1).isEqualTo("Entry added");

        String response2 = entryService.createEntry(createEntryRequest2);
        assertThat(response2).isNotNull();
        assertThat(response2).isEqualTo("Entry added");
    }

    @Test
    void getEntryById() {
    }

    @Test
    void updateEntry() {
    }

    @Test
    void numberOfEntries() {
    }

    @Test
    void deleteEntry() {
    }

    @Test
    void getAllEntries() {
    }
}