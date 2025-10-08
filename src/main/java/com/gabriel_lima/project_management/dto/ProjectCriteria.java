package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.PriorityStatus;
import com.gabriel_lima.project_management.domain.enums.ProgressStatus;
import lombok.Data;

@Data
public class ProjectCriteria {
    private String name;
    private ProgressStatus status;
    private PriorityStatus priority;
}
