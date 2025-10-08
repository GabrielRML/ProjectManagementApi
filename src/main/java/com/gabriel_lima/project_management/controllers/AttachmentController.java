package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.*;
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
    public ResponseEntity<ApiResponseDTO<List<AttachmentDTO>>> getAllAttachments() {
        List<AttachmentDTO> attachments = attachmentService.getAllAttachments();
        return ResponseEntity.ok(ApiResponseDTO.success(attachments, "Anexos recuperados com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AttachmentDTO>> getAttachmentById(@PathVariable String id) {
        AttachmentDTO attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(attachment, "Anexo encontrado com sucesso"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<AttachmentDTO>> createAttachment(@RequestBody AttachmentCreateDTO createDTO) {
        AttachmentDTO createdAttachment = attachmentService.createAttachment(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdAttachment, "Anexo criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AttachmentDTO>> updateAttachment(@PathVariable String id, @RequestBody AttachmentCreateDTO updateDTO) {
        AttachmentDTO updatedAttachment = attachmentService.updateAttachment(id, updateDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedAttachment, "Anexo atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAttachment(@PathVariable String id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Anexo excluído com sucesso"));
    }
}
