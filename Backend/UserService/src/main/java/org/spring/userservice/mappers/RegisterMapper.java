package org.spring.userservice.mappers;

import org.mapstruct.*;
import org.spring.userservice.models.Dtos.RegisterDto;
import org.spring.userservice.models.UserModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegisterMapper {
	UserModel toEntity(RegisterDto registerDto);

	RegisterDto toDto(UserModel userModel);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserModel partialUpdate(RegisterDto registerDto, @MappingTarget UserModel userModel);
}