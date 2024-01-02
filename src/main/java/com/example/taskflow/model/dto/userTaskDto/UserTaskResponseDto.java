package com.example.taskflow.model.dto.userTaskDto;


import com.example.taskflow.model.dto.taskDto.TaskResponseDto;
import com.example.taskflow.model.dto.userDto.UserResponseDto;

import java.time.LocalDateTime;

public record UserTaskResponseDto(
    Long id,
    LocalDateTime date,
    String taskAction,
    TaskResponseDto task,
    UserResponseDto appUser
) {
}
