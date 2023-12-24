package com.example.taskflow.domain.entities;

import com.example.taskflow.domain.entities.enums.RequestStatus;
import com.example.taskflow.domain.entities.enums.TypeRequest;
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
public class UserRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeRequest typeRequest;
    private RequestStatus requestStatus;
    private LocalDateTime dateRequest;
    @OneToMany(mappedBy = "userRequest") @JsonBackReference
    private Collection<UserTask> userTasks;
}
