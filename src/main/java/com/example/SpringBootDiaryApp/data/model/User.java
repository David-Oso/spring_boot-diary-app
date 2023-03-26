package com.example.SpringBootDiaryApp.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @Column(unique = true)
    private String email;
    private int age;
    private String password;
    private String userImage;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Diary diary;
    private LocalDateTime createdAt;
}
