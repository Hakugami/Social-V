package org.spring.likeservice.mappers;

import org.mapstruct.*;
import org.spring.likeservice.models.Dtos.LikeDto;
import org.spring.likeservice.models.LikeModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LikeMapper {
//    LikeModel toEntity(LikeRequest likeRequest);

    LikeModel toEntity(LikeDto likeDto);

    LikeDto toDto(LikeModel likeModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LikeModel partialUpdate(LikeDto likeDto, @MappingTarget LikeModel likeModel);
}
