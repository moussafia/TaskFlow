package com.example.taskflow.service;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.enums.TaskStatus;

import java.util.Set;

public interface TaskService {
    public Task createTask(Task task, Set<Tag> tag);
    public void addTagToTask(Task task, Set<Tag> tag);
    public Task updateStatusTask(Long id, TaskStatus taskStatus);
    public Task getTask(Long id);
    Task updateTask(Task task);
    public void deleteTask(Long id);
}
