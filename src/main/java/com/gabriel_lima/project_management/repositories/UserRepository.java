package com.gabriel_lima.project_management.repositories;

import com.gabriel_lima.project_management.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.projectUsers pu WHERE pu.project.id = :projectId")
    List<User> findAllUserByProjectId(UUID projectId);
}
