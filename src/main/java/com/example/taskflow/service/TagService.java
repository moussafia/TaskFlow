package com.example.taskflow.service;

import com.example.taskflow.entities.Tag;

import java.util.Set;

public interface TagService {
    Set<Tag> createTags(Set<Tag> tags);
}
