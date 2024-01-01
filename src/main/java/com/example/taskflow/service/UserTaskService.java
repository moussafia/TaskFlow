package com.example.taskflow.service;

import com.example.taskflow.entities.AppUser;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.entities.enums.TaskAction;
import com.example.taskflow.entities.enums.TaskStatus;

import java.util.Set;

public interface UserTaskService {
    public UserTask createTask(Task task, Set<Tag> tags);
    public UserTask updateStatusTask(Long id, TaskStatus taskStatus);
    public UserTask assignTaskToOther(Long task_id, Long user_id);
    public UserTask assignTaskToMySelf(Long task_id);
    public UserTask getTaskForUserByTaskAction(AppUser user, Long task_id, TaskAction taskAction);
    public void deleteTask(Long task_id);
}
