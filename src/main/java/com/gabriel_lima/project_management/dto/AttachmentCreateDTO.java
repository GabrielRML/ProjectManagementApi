package com.gabriel_lima.project_management.dto;

import com.gabriel_lima.project_management.domain.enums.AttachmentRelatedEntityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentCreateDTO {
    private String fileName;
    private String fileType;
    private String filePath;
    private String relatedEntityId;
    private AttachmentRelatedEntityType relatedEntityType;
}
