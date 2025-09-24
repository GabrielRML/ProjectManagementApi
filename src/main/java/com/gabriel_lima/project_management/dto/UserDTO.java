package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private UserRole role;
}
