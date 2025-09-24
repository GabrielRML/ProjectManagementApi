package com.gabriel_lima.project_management.domain.entities;

import com.gabriel_lima.project_management.domain.enums.AttachmentRelatedEntityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "related_entity_id", nullable = false)
    private UUID relatedEntityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "related_entity_type", nullable = false)
    private AttachmentRelatedEntityType relatedEntityType;
}
