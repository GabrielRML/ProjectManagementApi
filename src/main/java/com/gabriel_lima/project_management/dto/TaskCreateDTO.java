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
public class TaskCreateDTO {
    private String name;
    private String description;
    private Instant deadline;
    private Double estimatedHours;
    private int sequence;
    private ProgressStatus status;
    private PriorityStatus priority;
    private String sprintId;
    private String rootTaskId;
    private List<String> userIds;
}
