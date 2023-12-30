package com.example.taskflow.repository;

import com.example.taskflow.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole,Long> {
    Optional<AppRole> findByName(String roleName);
}
