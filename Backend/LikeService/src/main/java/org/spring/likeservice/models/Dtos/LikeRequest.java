package org.spring.likeservice.models.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class LikeRequest {
    Integer numberOfLikes;
    List<String> usernames;
    String postId;
}
