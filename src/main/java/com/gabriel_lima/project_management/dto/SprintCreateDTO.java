package com.gabriel_lima.project_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class SprintCreateDTO {
    private String name;
    private Instant initialDate;
    private Instant endDate;
    private int sequence;
    private String projectId;
}
