package com.example.taskflow.domain.entities;

import com.example.taskflow.domain.entities.enums.TaskAction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTask {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date;
    private TaskAction taskAction;
    @ManyToOne
    private Task task;
    @ManyToOne
    private UserT userT;
    @ManyToOne
    private UserRequest userRequest;
}
