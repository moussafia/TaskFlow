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
        validateDateTask(task.getDate());
        validateStartTimeTask(task.getStartTime());
        validateEndTimeTask(task.getEndTime(), task.getStartTime());
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
        task.getTags().addAll(tagsSaved);
    }
    @Override
    public Task getTask(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()-> new CustomValidationException("task not found"));
    }

    @Override
    public Task updateTask(Task task) {
        Task taskExisting = taskRepository.findById(task.getId())
                .orElseThrow(()-> new CustomValidationException("no task found for provided id"));
        return taskRepository.save(task);
    }

    @Override
    public Task updateStatusTask(Long id, TaskStatus taskStatus){
        Task task = getTask(id);
        validateUpdateDateOfTaskStatus(task.getDate(), task.getEndTime());
        task.setTaskStatus(taskStatus);
        return taskRepository.save(task);
    }
    @Override
    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new CustomValidationException("no task found for provided id"));;
        taskRepository.delete(task);
    }

    private void validateUpdateDateOfTaskStatus(LocalDate dateTask,
                                                LocalTime timeEndTask) {
        if(dateTask.isAfter(LocalDate.now())
        || (dateTask.equals(LocalDate.now()) && timeEndTask.isAfter(LocalTime.now()))){
            throw new CustomValidationException("Task status cannot be updated after the specified date limit.");
        }
    }

    private void validateEndTimeTask(LocalTime endTime, LocalTime startTime) {
        if (endTime.isBefore(startTime.plus(3, ChronoUnit.DAYS)))
            throw new ValidationException("End time must be at least 3 days after start time");
    }

    private void validateStartTimeTask(LocalTime startTime) {
        if (startTime.isBefore(LocalTime.now()))
            throw new ValidationException("start time beginning task should after or equals time now");
    }

    private void validateDateTask(LocalDate date) {
        if (date.isBefore(LocalDate.now()))
            throw new ValidationException("date limit of task should equal or after date now");
    }
}
