package com.gabriel_lima.project_management.repositories;

import com.gabriel_lima.project_management.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
