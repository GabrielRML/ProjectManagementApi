package com.gabriel_lima.project_management.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sprint")
@Getter
@Setter
@NoArgsConstructor
public class Sprint {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "initial_date")
    private Instant initialDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(nullable = false)
    private int sequence;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
