package org.spring.likeservice.models.Dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LikeDto implements Serializable {
    Integer numberOfLikes;
    List<LikeDetailsDto> likes;
}