package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Task;
import com.gabriel_lima.project_management.domain.entities.Timesheet;
import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.TimesheetCreateDTO;
import com.gabriel_lima.project_management.dto.TimesheetDTO;
import com.gabriel_lima.project_management.mapper.TimesheetMapper;
import com.gabriel_lima.project_management.repositories.TaskRepository;
import com.gabriel_lima.project_management.repositories.TimesheetRepository;
import com.gabriel_lima.project_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TimesheetService {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TimesheetMapper timesheetMapper;

    public List<TimesheetDTO> getAllTimesheets() {
        return timesheetRepository.findAll().stream()
                .map(timesheetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TimesheetDTO getTimesheetById(String id) {
        UUID timesheetId = UUID.fromString(id);
        Timesheet timesheet = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));
        return timesheetMapper.toDTO(timesheet);
    }

    public TimesheetDTO createTimesheet(TimesheetCreateDTO createDTO) {
        User user = userRepository.findById(UUID.fromString(createDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        UUID taskId = UUID.fromString(createDTO.getTaskId());
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Timesheet timesheet = timesheetMapper.toEntity(createDTO);
        timesheet.setUser(user);
        timesheet.setTask(task);

        Timesheet savedTimesheet = timesheetRepository.save(timesheet);
        return timesheetMapper.toDTO(savedTimesheet);
    }

    public TimesheetDTO updateTimesheet(String id, TimesheetCreateDTO updateDTO) {
        UUID timesheetId = UUID.fromString(id);
        Timesheet existingTimesheet = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

        existingTimesheet.setHoursWorked(updateDTO.getHoursWorked());
        existingTimesheet.setDate(updateDTO.getDate());
        existingTimesheet.setDescription(updateDTO.getDescription());

        Timesheet updatedTimesheet = timesheetRepository.save(existingTimesheet);
        return timesheetMapper.toDTO(updatedTimesheet);
    }

    public void deleteTimesheet(String id) {
        UUID timesheetId = UUID.fromString(id);
        if (!timesheetRepository.existsById(timesheetId)) {
            throw new RuntimeException("Timesheet not found");
        }
        timesheetRepository.deleteById(timesheetId);
    }
}
