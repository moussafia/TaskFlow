package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.enums.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.service.TaskService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    @Override
    public Task createTask(Task task){
        validateDateTask(task.getDate());
        validateTimeTask(task.getStartTime());
        task.setTaskStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    private void validateTimeTask(LocalTime startTime) {
        if (startTime.isBefore(LocalTime.now()))
            throw new ValidationException("start time of create task should after time now");
    }

    private void validateDateTask(LocalDate date) {
        if (date.isBefore(LocalDate.now()))
            throw new ValidationException("date limit of task should equal or after date now");
    }
}
