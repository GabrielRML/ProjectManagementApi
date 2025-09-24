package com.gabriel_lima.project_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDTO {
    private String name;
    private String email;
    private String password;
    private String role;
}
