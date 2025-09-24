package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.SprintCreateDTO;
import com.gabriel_lima.project_management.dto.SprintDTO;
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
    public ResponseEntity<List<SprintDTO>> getAllSprints() {
        List<SprintDTO> sprints = sprintService.getAllSprints();
        return ResponseEntity.ok(sprints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintDTO> getSprintById(@PathVariable String id) {
        SprintDTO sprint = sprintService.getSprintById(id);
        return ResponseEntity.ok(sprint);
    }

    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintCreateDTO createDTO) {
        SprintDTO createdSprint = sprintService.createSprint(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSprint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintDTO> updateSprint(@PathVariable String id, @RequestBody SprintCreateDTO updateDTO) {
        SprintDTO updatedSprint = sprintService.updateSprint(id, updateDTO);
        return ResponseEntity.ok(updatedSprint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable String id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }
}
