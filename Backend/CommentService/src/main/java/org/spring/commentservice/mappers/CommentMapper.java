package org.spring.commentservice.mappers;

import org.mapstruct.*;
import org.spring.commentservice.models.CommentModel;
import org.spring.commentservice.models.Dtos.CommentDto;
import org.spring.commentservice.models.Dtos.CommentRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    CommentModel toEntity(CommentRequest commentRequest);

    CommentDto toDto(CommentModel commentModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentModel partialUpdate(CommentDto commentDto, @MappingTarget CommentModel commentModel);
}
