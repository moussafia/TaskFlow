package com.example.taskflow.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
}
