package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.PriorityStatus;
import com.gabriel_lima.project_management.domain.enums.ProgressStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO {
    private String id;
    private String name;
    private String description;
    private Double plannedHours;
    private Instant initialDate;
    private Instant endDate;
    private ProgressStatus status;
    private PriorityStatus priority;
    private List<ProjectUserDTO> projectUsers;
}
