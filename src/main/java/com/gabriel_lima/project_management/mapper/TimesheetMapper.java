package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.Timesheet;
import com.gabriel_lima.project_management.dto.TimesheetCreateDTO;
import com.gabriel_lima.project_management.dto.TimesheetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class})
public interface TimesheetMapper {

    @Mapping(target = "id", expression = "java(timesheet.getId() != null ? timesheet.getId().toString() : null)")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "task", ignore = true)
    TimesheetDTO toDTO(Timesheet timesheet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "task", ignore = true)
    Timesheet toEntity(TimesheetCreateDTO createDTO);
}
