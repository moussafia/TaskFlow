package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.Exception.CustomValidationException;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.enums.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.service.TagService;
import com.example.taskflow.service.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TagService tagService;
    @Override
    public Task createTask(Task task, Set<Tag> tag){
        validateStartDateTask(task.getStartDate());
        validateEndDateTask(task.getStartDate(), task.getEndDate());
        task.setTaskStatus(TaskStatus.TODO);
        if (tag.size() < 2)
            throw new CustomValidationException("you should at least give 2 tag for this task");
        Task tasksaved = taskRepository.save(task);
        addTagToTask(tasksaved, tag);
        return tasksaved;
    }
    @Override
    public void addTagToTask(Task task, Set<Tag> tag){
        List<Tag> tagsSaved = tagService.createTags(tag);
        task.setTags(tagsSaved);
    }
    @Override
    public Task getTask(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()-> new CustomValidationException("task not found"));
    }

    @Override
    public Task updateTask(Task task) {
        taskRepository.findById(task.getId())
                .ifPresent(t -> {
                    throw new CustomValidationException("no task found for provided id");
                });
        return taskRepository.save(task);
    }

    @Override
    public Task updateStatusTask(Long id, TaskStatus taskStatus){
        Task task = getTask(id);
        validateUpdateDateOfTaskStatus(task.getEndDate());
        task.setTaskStatus(taskStatus);
        return taskRepository.save(task);
    }
    @Override
    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new CustomValidationException("no task found for provided id"));;
        taskRepository.delete(task);
    }

    private void validateUpdateDateOfTaskStatus(LocalDateTime timeEndTask) {
        if(timeEndTask.isAfter(LocalDateTime.now())){
            throw new CustomValidationException("Task status cannot be updated after the specified date limit.");
        }
    }

    private void validateStartDateTask(LocalDateTime startDate) {
        if (startDate.isBefore(LocalDateTime.now()))
            throw new CustomValidationException("start Date of task shouldn't be before or equals time now");
    }

    private void validateEndDateTask(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate.plus(3, ChronoUnit.DAYS)))
                throw new CustomValidationException("The end date and time of the task must be at least 3 days after the start date and time.");
    }
}
