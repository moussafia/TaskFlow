package com.example.taskflow.repository;

import com.example.taskflow.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository  extends JpaRepository<Tag,Long> {

}
