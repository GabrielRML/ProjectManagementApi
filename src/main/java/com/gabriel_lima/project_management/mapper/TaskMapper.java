package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.Task;
import com.gabriel_lima.project_management.dto.TaskCreateDTO;
import com.gabriel_lima.project_management.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SprintMapper.class, UserMapper.class})
public interface TaskMapper {

    @Mapping(target = "id", expression = "java(task.getId() != null ? task.getId().toString() : null)")
    @Mapping(target = "sprint", ignore = true)
    @Mapping(target = "rootTask", ignore = true)
    @Mapping(target = "users", ignore = true)
    TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sprint", ignore = true)
    @Mapping(target = "rootTask", ignore = true)
    @Mapping(target = "users", ignore = true)
    Task toEntity(TaskCreateDTO createDTO);
}
