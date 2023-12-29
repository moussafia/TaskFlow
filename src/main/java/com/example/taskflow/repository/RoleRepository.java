package com.example.taskflow.repository;

import com.example.taskflow.model.entities.RoleT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleT,Long> {
    Optional<RoleT> findByName(String roleName);
}
