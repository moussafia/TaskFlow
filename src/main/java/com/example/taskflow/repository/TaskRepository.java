package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository  extends JpaRepository<Task,Long> {
}
