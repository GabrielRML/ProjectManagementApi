package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.ProjectCreateDTO;
import com.gabriel_lima.project_management.dto.ProjectDTO;
import com.gabriel_lima.project_management.dto.ProjectUserCreateDTO;
import com.gabriel_lima.project_management.services.ProjectService;
import com.gabriel_lima.project_management.services.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectUserService projectUserService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectCreateDTO createDTO) {
        ProjectDTO createdProject = projectService.createProject(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<Void> addUsersToProject(
            @PathVariable String id,
            @RequestBody List<ProjectUserCreateDTO> usersDTO) {
        projectUserService.addUsersToProject(id, usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @RequestBody ProjectCreateDTO updateDTO) {
        ProjectDTO updatedProject = projectService.updateProject(id, updateDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
