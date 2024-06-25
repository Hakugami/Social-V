package org.spring.userservice.mappers;

import org.spring.userservice.models.Dtos.UserModelDto;
import org.spring.userservice.models.UserModel;

public class UserModelMapper {
    public static UserModelDto fromModelToDto(UserModel userModel) {
        return new UserModelDto(
                userModel.getUsername(),
                userModel.getEmail(),
                userModel.getStatus(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getAddress(),
                userModel.getGender(),
                userModel.getCountry(),
                userModel.getCity(),
                userModel.getBirthDate(),
                userModel.getPhoneNumber(),
                userModel.getProfilePicture(),
                userModel.getCoverPicture(),
                userModel.getUrl()
        );
    }

    public static UserModel fromDtoToModel(UserModelDto userModelDto) {
        UserModel model = new UserModel();
        model.setUsername(userModelDto.username());
        model.setEmail(userModelDto.email());
        model.setStatus(userModelDto.status());
        model.setFirstName(userModelDto.firstName());
        model.setLastName(userModelDto.lastName());
        model.setAddress(userModelDto.address());
        model.setGender(userModelDto.gender());
        model.setCountry(userModelDto.country());
        model.setCity(userModelDto.city());
        model.setBirthDate(userModelDto.birthDate());
        model.setPhoneNumber(userModelDto.phoneNumber());
        model.setProfilePicture(userModelDto.profilePicture());
        model.setCoverPicture(userModelDto.coverPicture());
        model.setUrl(userModelDto.url()); // Assuming you have a URL field in UserModelDto
        return model;
    }

}

