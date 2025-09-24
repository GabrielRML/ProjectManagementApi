package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Sprint;
import com.gabriel_lima.project_management.domain.entities.Task;
import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.TaskCreateDTO;
import com.gabriel_lima.project_management.dto.TaskDTO;
import com.gabriel_lima.project_management.mapper.TaskMapper;
import com.gabriel_lima.project_management.repositories.SprintRepository;
import com.gabriel_lima.project_management.repositories.TaskRepository;
import com.gabriel_lima.project_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Cacheable(value = "tasks", key = "'all'")
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskDTO getTaskById(String id) {
        UUID taskId = UUID.fromString(id);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskMapper.toDTO(task);
    }

    @CacheEvict(value = "tasks", key = "'all'")
    public TaskDTO createTask(TaskCreateDTO createDTO) {
        UUID sprintId = UUID.fromString(createDTO.getSprintId());
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        Task task = taskMapper.toEntity(createDTO);
        task.setSprint(sprint);

        if (createDTO.getRootTaskId() != null) {
            Task rootTask = taskRepository.findById(UUID.fromString(createDTO.getRootTaskId()))
                    .orElseThrow(() -> new RuntimeException("Root task not found"));
            task.setRootTask(rootTask);
        }

        if (createDTO.getUserIds() != null && !createDTO.getUserIds().isEmpty()) {
            List<UUID> userUuids = createDTO.getUserIds().stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
            List<User> users = userRepository.findAllById(userUuids);
            task.setUsers(users);
        }

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    @CachePut(value = "tasks", key = "#id")
    @CacheEvict(value = "tasks", key = "'all'")
    public TaskDTO updateTask(String id, TaskCreateDTO updateDTO) {
        UUID taskId = UUID.fromString(id);
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setName(updateDTO.getName());
        existingTask.setDescription(updateDTO.getDescription());
        existingTask.setDeadline(updateDTO.getDeadline());
        existingTask.setEstimatedHours(updateDTO.getEstimatedHours());
        existingTask.setSequence(updateDTO.getSequence());
        existingTask.setStatus(updateDTO.getStatus());
        existingTask.setPriority(updateDTO.getPriority());

        if (updateDTO.getUserIds() != null) {
            List<UUID> userUuids = updateDTO.getUserIds().stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
            List<User> users = userRepository.findAllById(userUuids);
            existingTask.setUsers(users);
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDTO(updatedTask);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteTask(String id) {
        UUID taskId = UUID.fromString(id);
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }
}
