package com.gabriel_lima.project_management.repositories;

import com.gabriel_lima.project_management.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
