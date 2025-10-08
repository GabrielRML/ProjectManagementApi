package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.*;
import com.gabriel_lima.project_management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TaskDTO>>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(ApiResponseDTO.success(tasks, "Tarefas recuperadas com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TaskDTO>> getTaskById(@PathVariable String id) {
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(task, "Tarefa encontrada com sucesso"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TaskDTO>> createTask(@RequestBody TaskCreateDTO createDTO) {
        TaskDTO createdTask = taskService.createTask(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdTask, "Tarefa criada com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TaskDTO>> updateTask(@PathVariable String id, @RequestBody TaskCreateDTO updateDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, updateDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedTask, "Tarefa atualizada com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Tarefa excluída com sucesso"));
    }
}
