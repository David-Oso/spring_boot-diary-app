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
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            joinColumns = @JoinColumn(
                    name = "diary_id",
                    foreignKey = @ForeignKey(
                    foreignKeyDefinition = "foreign key (diary_id) references diary(id) ON DELETE CASCADE"
            )),
            inverseJoinColumns = @JoinColumn(
                    name = "entries_id",
                    foreignKey = @ForeignKey(
                    foreignKeyDefinition = "foreign key (entries_id) references entry(id) ON DELETE CASCADE"
            ))
    )
    private Set<Entry> entries = new HashSet<>();
}
