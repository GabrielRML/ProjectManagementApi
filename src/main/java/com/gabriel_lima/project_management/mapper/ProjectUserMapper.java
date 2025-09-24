package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.ProjectUser;
import com.gabriel_lima.project_management.dto.ProjectUserCreateDTO;
import com.gabriel_lima.project_management.dto.ProjectUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProjectUserMapper {

    @Mapping(target = "id", expression = "java(projectUser.getId() != null ? projectUser.getId().toString() : null)")
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "projectUsers", ignore = true)
    ProjectUserDTO toDTO(ProjectUser projectUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "user", ignore = true)
    ProjectUser toEntity(ProjectUserCreateDTO createDTO);
}
