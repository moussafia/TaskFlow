package com.example.taskflow.service;

import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.UserTask;

public interface UserTaskService {
    public UserTask createTask(Task task);
}
