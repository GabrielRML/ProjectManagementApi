package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.UserRole;

public record CreateUserDTO(String name, String email, String password, UserRole role) {
}
