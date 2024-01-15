package com.example.taskflow.model.dto.taskDto;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ManyToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate ,
        TaskStatus taskStatus,
        boolean isChanged,
        boolean isAlreadyAssigned,
        Collection<Tag> tags
) {
}
