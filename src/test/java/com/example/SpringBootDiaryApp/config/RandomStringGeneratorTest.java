package com.example.SpringBootDiaryApp.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class RandomStringGeneratorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateRandomString() {
        String string = RandomStringGenerator.generateRandomString(6);
        System.out.println(string);
        assertThat(string.length()).isEqualTo(8);

        String string2 = RandomStringGenerator.generateRandomString(8);
        assertThat(string2.length()).isEqualTo(11);
    }
}