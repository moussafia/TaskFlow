package com.example.taskflow.entities;

import com.example.taskflow.entities.enums.TaskAction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTask {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private TaskAction taskAction;
    @ManyToOne
    private Task task;
    @ManyToOne
    private AppUser appUser;
    @OneToMany(mappedBy = "userTask", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Collection<UserRequest> userRequests;
}
