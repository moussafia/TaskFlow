package com.example.taskflow.domain.entities;

import com.example.taskflow.domain.entities.enums.TaskStatus;
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
    private long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private TaskStatus taskStatus;
    @ManyToMany
    private Collection<Tag> tags;
    @OneToMany(mappedBy = "task")
    @JsonBackReference
    private Collection<UserTask> userTasks;
}
