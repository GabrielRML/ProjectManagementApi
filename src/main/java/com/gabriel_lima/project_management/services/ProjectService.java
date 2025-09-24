package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Project;
import com.gabriel_lima.project_management.dto.ProjectCreateDTO;
import com.gabriel_lima.project_management.dto.ProjectDTO;
import com.gabriel_lima.project_management.mapper.ProjectMapper;
import com.gabriel_lima.project_management.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Cacheable(value = "projects", key = "'all'")
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "projects", key = "#id")
    public ProjectDTO getProjectById(String id) {
        UUID projectId = UUID.fromString(id);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toDTO(project);
    }

    @CacheEvict(value = "projects", key = "'all'")
    public ProjectDTO createProject(ProjectCreateDTO createDTO) {
        Project project = projectMapper.toEntity(createDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @CachePut(value = "projects", key = "#id")
    @CacheEvict(value = "projects", key = "'all'")
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

    @Caching(evict = {
            @CacheEvict(value = "projects", key = "#id"),
            @CacheEvict(value = "projects", key = "'all'")
    })
    public void deleteProject(String id) {
        UUID projectId = UUID.fromString(id);
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(projectId);
    }
}
