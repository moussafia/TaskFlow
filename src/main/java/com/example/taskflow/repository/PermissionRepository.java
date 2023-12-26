package com.example.taskflow.repository;

import com.example.taskflow.model.entities.PermissionT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionT, Long> {
}
