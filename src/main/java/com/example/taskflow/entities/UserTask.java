package com.example.taskflow.entities;

import com.example.taskflow.entities.enums.TaskAction;
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
    private Long id;
    private LocalDateTime date;
    private TaskAction taskAction;
    @ManyToOne
    private Task task;
    @ManyToOne
    private UserT userT;
    @ManyToOne
    private UserRequest userRequest;
}
