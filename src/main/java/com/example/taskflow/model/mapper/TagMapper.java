package com.example.taskflow.model.mapper;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.model.dto.tagDto.TagRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TagMapper {
    Class<? extends TagMapper> INSTANCE = Mappers.getMapperClass(TagMapper.class);
    @Mapping(target ="id" ,ignore = true)
    @Mapping(target ="name" ,source = "name")
    Tag tagDtoToTag(TagRequestDto tagRequestDto);
    default Set<Tag> tagDtoListToTagList(Set<TagRequestDto> tags){
        return tags.stream()
                .map(this::tagDtoToTag)
                .collect(Collectors.toSet());
    }
}
