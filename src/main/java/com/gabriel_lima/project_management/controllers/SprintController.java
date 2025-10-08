package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.*;
import com.gabriel_lima.project_management.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<SprintDTO>>> getAllSprints() {
        List<SprintDTO> sprints = sprintService.getAllSprints();
        return ResponseEntity.ok(ApiResponseDTO.success(sprints, "Sprints recuperados com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SprintDTO>> getSprintById(@PathVariable String id) {
        SprintDTO sprint = sprintService.getSprintById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(sprint, "Sprint encontrado com sucesso"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<SprintDTO>> createSprint(@RequestBody SprintCreateDTO createDTO) {
        SprintDTO createdSprint = sprintService.createSprint(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdSprint, "Sprint criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SprintDTO>> updateSprint(@PathVariable String id, @RequestBody SprintCreateDTO updateDTO) {
        SprintDTO updatedSprint = sprintService.updateSprint(id, updateDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedSprint, "Sprint atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSprint(@PathVariable String id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Sprint excluído com sucesso"));
    }
}
