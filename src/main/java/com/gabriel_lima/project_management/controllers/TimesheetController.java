package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.*;
import com.gabriel_lima.project_management.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TimesheetDTO>>> getAllTimesheets() {
        List<TimesheetDTO> timesheets = timesheetService.getAllTimesheets();
        return ResponseEntity.ok(ApiResponseDTO.success(timesheets, "Timesheets recuperados com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TimesheetDTO>> getTimesheetById(@PathVariable String id) {
        TimesheetDTO timesheet = timesheetService.getTimesheetById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(timesheet, "Timesheet encontrado com sucesso"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TimesheetDTO>> createTimesheet(@RequestBody TimesheetCreateDTO createDTO) {
        TimesheetDTO createdTimesheet = timesheetService.createTimesheet(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdTimesheet, "Timesheet criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TimesheetDTO>> updateTimesheet(@PathVariable String id, @RequestBody TimesheetCreateDTO updateDTO) {
        TimesheetDTO updatedTimesheet = timesheetService.updateTimesheet(id, updateDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedTimesheet, "Timesheet atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTimesheet(@PathVariable String id) {
        timesheetService.deleteTimesheet(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Timesheet excluído com sucesso"));
    }
}
