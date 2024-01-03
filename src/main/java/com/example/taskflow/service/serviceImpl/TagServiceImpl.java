package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.repository.TagRepository;
import com.example.taskflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Override
    public List<Tag> createTags(Set<Tag> tags) {
        Set<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        List<Tag> tagExisting = fetchExistingTagInList(tagNames);
        if(!tagExisting.isEmpty()){
            return createAndFilterTag(tagExisting, tags);
        }
        return tagRepository.saveAll(tags);
    }
    @Override
    public List<Tag> fetchAllTags(){
        return tagRepository.findAll();
    }
    private List<Tag> fetchExistingTagInList(Set<String> tagNames){
        return tagRepository.fetchTagExistingInList(tagNames)
                .orElse(null);
    }
    private List<Tag> createAndFilterTag(List<Tag> tagExisting, Set<Tag> tags){
        List<String> tagExistingName = tagExisting.stream().map(Tag::getName)
                .toList();
        List<Tag> savingTag = tags.stream()
                .filter(tag -> !tagExistingName.contains(tag.getName()))
                .map(tagRepository::save)
                .toList();
        List<Tag> tagsSaved = new ArrayList<>();
        tagsSaved.addAll(tagExisting);
        tagsSaved.addAll(savingTag);
        return tagsSaved;
    }
}
