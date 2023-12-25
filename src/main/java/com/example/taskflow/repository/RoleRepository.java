package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.RoleT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleT,Long> {
}
