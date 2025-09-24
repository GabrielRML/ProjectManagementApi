package com.gabriel_lima.project_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TimesheetDTO {
    private String id;
    private Double hoursWorked;
    private Instant date;
    private String description;
    private UserDTO user;
    private TaskDTO task;
}
