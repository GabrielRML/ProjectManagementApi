package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.*;
import com.gabriel_lima.project_management.services.ProjectService;
import com.gabriel_lima.project_management.services.ProjectUserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<PagedResponseDTO<ProjectDTO>>> getAllProjectsWithCriteria(
        @ParameterObject ProjectCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        Page<ProjectDTO> projects = projectService.getAllProjectsWithCriteria(criteria, pageable);
        PagedResponseDTO<ProjectDTO> pagedResponse = PagedResponseDTO.from(projects);
        return ResponseEntity.ok(ApiResponseDTO.success(pagedResponse, "Projetos recuperados com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProjectDTO>> getProjectById(@PathVariable String id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(project, "Projeto encontrado com sucesso"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ProjectDTO>> createProject(@RequestBody ProjectCreateDTO createDTO) {
        ProjectDTO createdProject = projectService.createProject(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdProject, "Projeto criado com sucesso"));
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<ApiResponseDTO<Void>> addUsersToProject(
            @PathVariable String id,
            @RequestBody List<ProjectUserCreateDTO> usersDTO) {
        projectUserService.addUsersToProject(id, usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(null, "Usuários adicionados ao projeto com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProjectDTO>> updateProject(@PathVariable String id, @RequestBody ProjectCreateDTO updateDTO) {
        ProjectDTO updatedProject = projectService.updateProject(id, updateDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedProject, "Projeto atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Projeto excluído com sucesso"));
    }
}
