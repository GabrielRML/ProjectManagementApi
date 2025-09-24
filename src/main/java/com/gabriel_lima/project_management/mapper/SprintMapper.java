package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.Sprint;
import com.gabriel_lima.project_management.dto.SprintCreateDTO;
import com.gabriel_lima.project_management.dto.SprintDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SprintMapper {

    @Mapping(target = "id", expression = "java(sprint.getId() != null ? sprint.getId().toString() : null)")
    @Mapping(target = "project", ignore = true)
    SprintDTO toDTO(Sprint sprint);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    Sprint toEntity(SprintCreateDTO createDTO);
}
