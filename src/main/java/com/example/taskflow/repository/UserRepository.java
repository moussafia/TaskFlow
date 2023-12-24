package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.UserT;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserT,Long> {
    public Optional<UserT> findByEmail(String email);
}
