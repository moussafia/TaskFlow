package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskRepository  extends JpaRepository<UserTask,Long> {
}
