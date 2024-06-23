package org.spring.postservice.models.Dtos;

import java.io.Serializable;

public record PictureDto(String profilePicture , String username) implements Serializable {
}
