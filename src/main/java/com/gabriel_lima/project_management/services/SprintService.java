package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Project;
import com.gabriel_lima.project_management.domain.entities.Sprint;
import com.gabriel_lima.project_management.dto.SprintCreateDTO;
import com.gabriel_lima.project_management.dto.SprintDTO;
import com.gabriel_lima.project_management.mapper.SprintMapper;
import com.gabriel_lima.project_management.repositories.ProjectRepository;
import com.gabriel_lima.project_management.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SprintMapper sprintMapper;

    @Cacheable(value = "sprints", key = "'all'")
    public List<SprintDTO> getAllSprints() {
        return sprintRepository.findAll().stream()
                .map(sprintMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "sprints", key = "#id")
    public SprintDTO getSprintById(String id) {
        UUID sprintId = UUID.fromString(id);
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));
        return sprintMapper.toDTO(sprint);
    }

    @CacheEvict(value = "sprints", key = "'all'")
    public SprintDTO createSprint(SprintCreateDTO createDTO) {
        UUID projectId = UUID.fromString(createDTO.getProjectId());
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Sprint sprint = sprintMapper.toEntity(createDTO);
        sprint.setProject(project);

        Sprint savedSprint = sprintRepository.save(sprint);
        return sprintMapper.toDTO(savedSprint);
    }

    @CachePut(value = "sprints", key = "#id")
    @CacheEvict(value = "sprints", key = "'all'")
    public SprintDTO updateSprint(String id, SprintCreateDTO updateDTO) {
        UUID sprintId = UUID.fromString(id);
        Sprint existingSprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        existingSprint.setName(updateDTO.getName());
        existingSprint.setInitialDate(updateDTO.getInitialDate());
        existingSprint.setEndDate(updateDTO.getEndDate());
        existingSprint.setSequence(updateDTO.getSequence());

        Sprint updatedSprint = sprintRepository.save(existingSprint);
        return sprintMapper.toDTO(updatedSprint);
    }

    @CacheEvict(value = "sprints", allEntries = true)
    public void deleteSprint(String id) {
        UUID sprintId = UUID.fromString(id);
        if (!sprintRepository.existsById(sprintId)) {
            throw new RuntimeException("Sprint not found");
        }
        sprintRepository.deleteById(sprintId);
    }
}
