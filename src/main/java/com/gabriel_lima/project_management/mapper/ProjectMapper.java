package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.Project;
import com.gabriel_lima.project_management.dto.ProjectCreateDTO;
import com.gabriel_lima.project_management.dto.ProjectDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProjectUserMapper.class})
public interface ProjectMapper {

    ProjectDTO toDTO(Project project);

    Project toEntity(ProjectCreateDTO createDTO);
}
