package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.UserCreateDTO;
import com.gabriel_lima.project_management.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", expression = "java(user.getId() != null ? user.getId().toString() : null)")
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(com.gabriel_lima.project_management.domain.enums.UserRole.valueOf(createDTO.getRole().toUpperCase()))")
    @Mapping(target = "projectUsers", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toEntity(UserCreateDTO createDTO);
}
