package com.example.taskflow.model.dto.taskDto;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.enums.TaskStatus;
import com.example.taskflow.model.dto.tagDto.TagRequestDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

public record CreateTaskRequest(
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Set<TagRequestDto> tags

) {
}
