package com.gabriel_lima.project_management.repositories;

import com.gabriel_lima.project_management.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
