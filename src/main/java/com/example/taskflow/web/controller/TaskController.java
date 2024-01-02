package com.example.taskflow.web.controller;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.model.dto.taskDto.CreateTaskRequest;
import com.example.taskflow.model.dto.taskDto.TaskResponseDto;
import com.example.taskflow.model.mapper.TagMapper;
import com.example.taskflow.model.mapper.TaskMapper;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.service.UserTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final UserTaskService userTaskService;
    @PostMapping
    public TaskResponseDto createTask(@RequestBody
                                          @Valid CreateTaskRequest createTaskRequest)
            throws InstantiationException, IllegalAccessException {
        Task task = TaskMapper.INSTANCE.newInstance().createTaskRequestToTask(createTaskRequest);
        Set<Tag> tags = TagMapper.INSTANCE.newInstance().tagDtoListToTagList(createTaskRequest.tags());
        UserTask taskSaved = userTaskService.createTask(task, tags);
        System.out.println(taskSaved);

        return null;
    }

}
