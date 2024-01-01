package com.example.taskflow.repository;

import com.example.taskflow.entities.UserRequest;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.entities.enums.RequestStatus;
import org.apache.coyote.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRequestRepository  extends JpaRepository<UserRequest,Long> {
    Optional<UserRequest> findByUserTask(UserTask userTask);
}
