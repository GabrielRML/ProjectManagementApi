package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.TimesheetCreateDTO;
import com.gabriel_lima.project_management.dto.TimesheetDTO;
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
    public ResponseEntity<List<TimesheetDTO>> getAllTimesheets() {
        List<TimesheetDTO> timesheets = timesheetService.getAllTimesheets();
        return ResponseEntity.ok(timesheets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimesheetDTO> getTimesheetById(@PathVariable String id) {
        TimesheetDTO timesheet = timesheetService.getTimesheetById(id);
        return ResponseEntity.ok(timesheet);
    }

    @PostMapping
    public ResponseEntity<TimesheetDTO> createTimesheet(@RequestBody TimesheetCreateDTO createDTO) {
        TimesheetDTO createdTimesheet = timesheetService.createTimesheet(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimesheet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimesheetDTO> updateTimesheet(@PathVariable String id, @RequestBody TimesheetCreateDTO updateDTO) {
        TimesheetDTO updatedTimesheet = timesheetService.updateTimesheet(id, updateDTO);
        return ResponseEntity.ok(updatedTimesheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable String id) {
        timesheetService.deleteTimesheet(id);
        return ResponseEntity.noContent().build();
    }
}
