package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.repository.TagRepository;
import com.example.taskflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Set<String> tagsName = new HashSet<>();
        for (Tag tag1 : tags) {
            String name = tag1.getName();
            tagsName.add(name);
        }
        return createAndFilterTag(fetchAllTags(), tagsName);
    }
    @Override
    public List<Tag> fetchAllTags(){
        return tagRepository.findAll();
    }
    private List<Tag> createAndFilterTag(List<Tag> tags, Set<String> tagsName){
        return tags.stream()
                .map(tag->{
                    if(tagsName.contains(tag.getName())){
                        return tag;
                    }else {
                        return tagRepository.save(tag);
                    }
                }).toList();
    }
}
