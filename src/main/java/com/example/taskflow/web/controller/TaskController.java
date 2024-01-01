package com.example.taskflow.web.controller;

import com.example.taskflow.model.dto.taskDto.CreateTaskRequest;
import com.example.taskflow.model.dto.taskDto.TaskResponseDto;
import com.example.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponseDto createTask(@RequestBody
                                          @Valid CreateTaskRequest createTaskRequest){
        return null;
    }

}
