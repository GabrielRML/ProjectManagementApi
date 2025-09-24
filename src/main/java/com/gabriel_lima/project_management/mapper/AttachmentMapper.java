package com.gabriel_lima.project_management.mapper;

import com.gabriel_lima.project_management.domain.entities.Attachment;
import com.gabriel_lima.project_management.dto.AttachmentCreateDTO;
import com.gabriel_lima.project_management.dto.AttachmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(target = "id", expression = "java(attachment.getId() != null ? attachment.getId().toString() : null)")
    @Mapping(target = "relatedEntityId", expression = "java(attachment.getRelatedEntityId() != null ? attachment.getRelatedEntityId().toString() : null)")
    AttachmentDTO toDTO(Attachment attachment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "relatedEntityId", expression = "java(createDTO.getRelatedEntityId() != null ? java.util.UUID.fromString(createDTO.getRelatedEntityId()) : null)")
    Attachment toEntity(AttachmentCreateDTO createDTO);
}
