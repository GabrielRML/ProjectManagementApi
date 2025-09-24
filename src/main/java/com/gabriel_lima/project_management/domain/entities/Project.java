package com.gabriel_lima.project_management.domain.entities;

import com.gabriel_lima.project_management.domain.enums.PriorityStatus;
import com.gabriel_lima.project_management.domain.enums.ProgressStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "planned_hours")
    private Double plannedHours;

    @Column(name = "initial_date")
    private Instant initialDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityStatus priority;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ProjectUser> projectUsers;
}
