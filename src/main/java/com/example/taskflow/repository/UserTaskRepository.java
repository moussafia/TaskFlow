package com.example.taskflow.repository;

import com.example.taskflow.entities.AppUser;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.entities.enums.TaskAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTaskRepository  extends JpaRepository<UserTask,Long> {
    Optional<UserTask> findByTask(Task task);
    Optional<UserTask> findByTaskAndAppUserAndTaskAction(Task task, AppUser user, TaskAction taskAction);
}
