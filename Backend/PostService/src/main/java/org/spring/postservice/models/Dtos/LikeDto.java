package org.spring.postservice.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto implements Serializable {
    Integer numberOfLikes;
    List<LikeDetailsDto> likes;
}