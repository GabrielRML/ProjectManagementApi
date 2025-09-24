package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.AttachmentRelatedEntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentDTO {
    private String id;
    private String fileName;
    private String fileType;
    private String filePath;
    private String relatedEntityId;
    private AttachmentRelatedEntityType relatedEntityType;
}
