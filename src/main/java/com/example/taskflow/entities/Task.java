package com.example.taskflow.entities;

import com.example.taskflow.entities.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TaskStatus taskStatus;
    private boolean isChanged = false ;
    private boolean isAlreadyAssigned = false;
    @ManyToMany
    @JsonBackReference
    private Collection<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Collection<UserTask> userTasks;

}
