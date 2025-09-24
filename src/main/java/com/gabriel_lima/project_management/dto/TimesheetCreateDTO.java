package com.gabriel_lima.project_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TimesheetCreateDTO {
    private Double hoursWorked;
    private Instant date;
    private String description;
    private String userId;
    private String taskId;
}
