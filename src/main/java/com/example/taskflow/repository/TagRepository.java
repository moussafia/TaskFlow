package com.example.taskflow.repository;

import com.example.taskflow.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository  extends JpaRepository<Tag,Long> {
}
