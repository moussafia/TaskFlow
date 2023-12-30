package com.example.taskflow.repository;

import com.example.taskflow.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<AppUser,Long> {
    public Optional<AppUser> findByEmail(String email);
}
