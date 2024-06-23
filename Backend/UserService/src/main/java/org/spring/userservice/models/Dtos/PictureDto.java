package org.spring.userservice.models.Dtos;

import java.io.Serializable;

public record PictureDto(String profilePicture , String username) implements Serializable {
}
