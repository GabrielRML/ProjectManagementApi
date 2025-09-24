package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.AttachmentCreateDTO;
import com.gabriel_lima.project_management.dto.AttachmentDTO;
import com.gabriel_lima.project_management.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments() {
        List<AttachmentDTO> attachments = attachmentService.getAllAttachments();
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDTO> getAttachmentById(@PathVariable String id) {
        AttachmentDTO attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(attachment);
    }

    @PostMapping
    public ResponseEntity<AttachmentDTO> createAttachment(@RequestBody AttachmentCreateDTO createDTO) {
        AttachmentDTO createdAttachment = attachmentService.createAttachment(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttachment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttachmentDTO> updateAttachment(@PathVariable String id, @RequestBody AttachmentCreateDTO updateDTO) {
        AttachmentDTO updatedAttachment = attachmentService.updateAttachment(id, updateDTO);
        return ResponseEntity.ok(updatedAttachment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable String id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }
}
