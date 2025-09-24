package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.UserRole;

public record AccountDTO(String id, String name, String email, UserRole role) {
}
