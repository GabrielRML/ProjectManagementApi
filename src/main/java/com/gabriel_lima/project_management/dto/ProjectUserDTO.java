package com.gabriel_lima.project_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectUserDTO {
    private String id;
    private ProjectDTO project;
    private UserDTO user;
    private Double allocationPercentage;
    private List<ProjectUserDTO> projectUsers;
}
