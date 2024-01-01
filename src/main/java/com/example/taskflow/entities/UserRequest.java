package com.example.taskflow.entities;

import com.example.taskflow.entities.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RequestStatus requestStatus;
    private LocalDateTime dateRequest;
    @ManyToOne
    private UserTask userTask;
}
