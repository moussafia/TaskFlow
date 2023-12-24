package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository  extends JpaRepository<UserTask,Long> {
}
