package com.example.taskflow.entities;

import com.example.taskflow.entities.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Entity @Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private TaskStatus taskStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean isChanged;
    @Column(columnDefinition = "boolean default false")
    private boolean isAlreadyAssigned;
    @ManyToMany
    private Collection<Tag> tags;
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Collection<UserTask> userTasks;

}
