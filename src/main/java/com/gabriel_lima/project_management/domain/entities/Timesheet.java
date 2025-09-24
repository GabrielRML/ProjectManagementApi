package com.gabriel_lima.project_management.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "timesheet")
@Getter
@Setter
@NoArgsConstructor
public class Timesheet {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "hours_worked", nullable = false)
    private Double hoursWorked;

    @Column(nullable = false)
    private Instant date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
