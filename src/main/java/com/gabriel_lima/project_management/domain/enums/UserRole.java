package com.gabriel_lima.project_management.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    LEADER("LEADER"),
    MEMBER("MEMBER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}
