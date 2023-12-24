package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository  extends JpaRepository<UserRequest,Long> {
}
