package com.example.SpringBootDiaryApp.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Diary diary;
    private boolean isEnabled = false;
    @CreationTimestamp
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private String profileImage;
}
