package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Project;
import com.gabriel_lima.project_management.domain.entities.ProjectUser;
import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.ProjectUserCreateDTO;
import com.gabriel_lima.project_management.dto.ProjectUserDTO;
import com.gabriel_lima.project_management.mapper.ProjectUserMapper;
import com.gabriel_lima.project_management.repositories.ProjectRepository;
import com.gabriel_lima.project_management.repositories.ProjectUserRepository;
import com.gabriel_lima.project_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectUserService {

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public void addUsersToProject(String projectId, List<ProjectUserCreateDTO> usersDTO) {
        UUID projId = UUID.fromString(projectId);
        Project project = projectRepository.findById(projId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        for (ProjectUserCreateDTO dto : usersDTO) {
            User user = userRepository.findById(UUID.fromString(dto.getUserId()))
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            ProjectUser projectUser = new ProjectUser();
            projectUser.setProject(project);
            projectUser.setUser(user);
            projectUser.setAllocationPercentage(dto.getAllocationPercentage());
            projectUserRepository.save(projectUser);
        }
    }
}
