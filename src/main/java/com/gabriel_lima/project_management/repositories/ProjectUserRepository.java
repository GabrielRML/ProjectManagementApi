package com.gabriel_lima.project_management.repositories;

import com.gabriel_lima.project_management.domain.entities.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {
}
