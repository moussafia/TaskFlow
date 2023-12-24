package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.PermissionT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionT, Long> {
}
