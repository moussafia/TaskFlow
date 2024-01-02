package com.example.taskflow.service;

import com.example.taskflow.entities.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    public List<Tag> createTags(Set<Tag> tags);
    public List<Tag> fetchAllTags();
}
