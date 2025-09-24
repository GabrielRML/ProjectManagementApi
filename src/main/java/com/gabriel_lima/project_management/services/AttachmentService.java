package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.Attachment;
import com.gabriel_lima.project_management.dto.AttachmentCreateDTO;
import com.gabriel_lima.project_management.dto.AttachmentDTO;
import com.gabriel_lima.project_management.mapper.AttachmentMapper;
import com.gabriel_lima.project_management.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentMapper attachmentMapper;

    public List<AttachmentDTO> getAllAttachments() {
        return attachmentRepository.findAll().stream()
                .map(attachmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AttachmentDTO getAttachmentById(String id) {
        UUID attachmentId = UUID.fromString(id);
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
        return attachmentMapper.toDTO(attachment);
    }

    public AttachmentDTO createAttachment(AttachmentCreateDTO createDTO) {
        Attachment attachment = attachmentMapper.toEntity(createDTO);
        Attachment savedAttachment = attachmentRepository.save(attachment);
        return attachmentMapper.toDTO(savedAttachment);
    }

    public AttachmentDTO updateAttachment(String id, AttachmentCreateDTO updateDTO) {
        UUID attachmentId = UUID.fromString(id);
        Attachment existingAttachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        existingAttachment.setFileName(updateDTO.getFileName());
        existingAttachment.setFileType(updateDTO.getFileType());
        existingAttachment.setFilePath(updateDTO.getFilePath());
        existingAttachment.setRelatedEntityId(UUID.fromString(updateDTO.getRelatedEntityId()));
        existingAttachment.setRelatedEntityType(updateDTO.getRelatedEntityType());

        Attachment updatedAttachment = attachmentRepository.save(existingAttachment);
        return attachmentMapper.toDTO(updatedAttachment);
    }

    public void deleteAttachment(String id) {
        UUID attachmentId = UUID.fromString(id);
        if (!attachmentRepository.existsById(attachmentId)) {
            throw new RuntimeException("Attachment not found");
        }
        attachmentRepository.deleteById(attachmentId);
    }
}
