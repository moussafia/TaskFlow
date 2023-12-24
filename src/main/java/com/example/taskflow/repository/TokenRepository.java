package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository  extends JpaRepository<Token,Long> {
}
