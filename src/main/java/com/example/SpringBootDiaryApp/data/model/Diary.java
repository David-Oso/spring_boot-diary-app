package com.example.SpringBootDiaryApp.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @OneToMany(mappedBy = "diary", cascade = CascadeType.DETACH, fetch = FetchType.EAGER, orphanRemoval = true)
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Entry> entries = new HashSet<>();
}
