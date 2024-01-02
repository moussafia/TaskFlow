package com.example.taskflow.web.controller;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.model.dto.taskDto.CreateTaskRequest;
import com.example.taskflow.model.dto.userTaskDto.UserTaskResponseDto;
import com.example.taskflow.model.mapper.TagMapper;
import com.example.taskflow.model.mapper.TaskMapper;
import com.example.taskflow.model.mapper.UserTaskMapper;
import com.example.taskflow.service.UserTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserTaskResponseDto> createTask(@RequestBody
                                          @Valid CreateTaskRequest createTaskRequest)
            throws InstantiationException, IllegalAccessException {
        Task task = TaskMapper.INSTANCE.newInstance().createTaskRequestToTask(createTaskRequest);
        Set<Tag> tags = TagMapper.INSTANCE.newInstance().tagDtoListToTagList(createTaskRequest.tags());
        UserTask userTaskSaved = userTaskService.createTask(task, tags);
        UserTaskResponseDto userTaskSavedDto = UserTaskMapper.INSTANCE.newInstance().toUserTaskDto(userTaskSaved);
        return ResponseEntity.ok().body(userTaskSavedDto);
    }

}
