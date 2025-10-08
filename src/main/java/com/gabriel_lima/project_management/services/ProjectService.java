package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Project;
import com.gabriel_lima.project_management.dto.ProjectCreateDTO;
import com.gabriel_lima.project_management.dto.ProjectCriteria;
import com.gabriel_lima.project_management.dto.ProjectDTO;
import com.gabriel_lima.project_management.mapper.ProjectMapper;
import com.gabriel_lima.project_management.repositories.ProjectRepository;
import com.gabriel_lima.project_management.specifications.ProjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    public Page<ProjectDTO> getAllProjectsWithCriteria(ProjectCriteria criteria, Pageable pageable) {
        Specification<Project> spec = ProjectSpecification.createSpecification(criteria);
        Page<Project> projectPage = projectRepository.findAll(spec, pageable);
        return projectPage.map(projectMapper::toDTO);
    }

    public ProjectDTO getProjectById(String id) {
        UUID projectId = UUID.fromString(id);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toDTO(project);
    }

    public ProjectDTO createProject(ProjectCreateDTO createDTO) {
        Project project = projectMapper.toEntity(createDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    public ProjectDTO updateProject(String id, ProjectCreateDTO updateDTO) {
        UUID projectId = UUID.fromString(id);
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        existingProject.setName(updateDTO.getName());
        existingProject.setDescription(updateDTO.getDescription());
        existingProject.setPlannedHours(updateDTO.getPlannedHours());
        existingProject.setInitialDate(updateDTO.getInitialDate());
        existingProject.setEndDate(updateDTO.getEndDate());

        Project updatedProject = projectRepository.save(existingProject);
        return projectMapper.toDTO(updatedProject);
    }

    public void deleteProject(String id) {
        UUID projectId = UUID.fromString(id);
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(projectId);
    }
}
