package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.RoleT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleT,Long> {
}
