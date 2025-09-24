package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.PriorityStatus;
import com.gabriel_lima.project_management.domain.enums.ProgressStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ProjectCreateDTO {
    private String name;
    private String description;
    private ProgressStatus status;
    private PriorityStatus priority;
    private Double plannedHours;
    private Instant initialDate;
    private Instant endDate;
}
