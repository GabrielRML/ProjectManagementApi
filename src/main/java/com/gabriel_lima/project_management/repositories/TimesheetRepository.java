package com.gabriel_lima.project_management.repositories;

import com.gabriel_lima.project_management.domain.entities.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimesheetRepository extends JpaRepository<Timesheet, UUID> {
}
