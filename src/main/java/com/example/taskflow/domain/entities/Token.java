package com.example.taskflow.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int TokenPerDayAvailable;
    private int TokenPerMonthAvailable;
}
