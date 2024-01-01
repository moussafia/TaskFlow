package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.repository.TagRepository;
import com.example.taskflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Override
    public List<Tag> createTags(Set<Tag> tags) {
        Set<Tag> tagFiltered = filterTags(tags);
        return tagRepository.saveAll(tagFiltered);
    }
    public List<Tag> fetchAllTags(){
        return tagRepository.findAll();
    }
    private Set<Tag> filterTags(Set<Tag> tags){
        List<String> existingTags = fetchAllTags()
                .stream()
                .map(Tag::getName)
                .toList();
        return tags.stream()
                .filter(tag -> !existingTags.contains(tag.getName()))
                .collect(Collectors.toSet());
    }
}
