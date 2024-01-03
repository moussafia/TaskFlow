package com.example.taskflow.repository;

import com.example.taskflow.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository  extends JpaRepository<Tag,Long> {
    @Query("SELECT t FROM Tag t WHERE t.name IN :tagNames")
    Optional<List<Tag>> fetchTagExistingInList(@Param("tagNames") Set<String> tagNames);

}
