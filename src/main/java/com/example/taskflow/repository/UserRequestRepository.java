package com.example.taskflow.repository;

import com.example.taskflow.model.entities.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository  extends JpaRepository<UserRequest,Long> {
}
