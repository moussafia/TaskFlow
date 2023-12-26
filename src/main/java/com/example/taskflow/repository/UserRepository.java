package com.example.taskflow.repository;

import com.example.taskflow.model.entities.UserT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<UserT,Long> {
    public Optional<UserT> findByEmail(String email);
}
