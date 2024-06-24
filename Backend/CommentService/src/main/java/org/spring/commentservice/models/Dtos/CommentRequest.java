package org.spring.commentservice.models.Dtos;

import lombok.Data;

@Data
public class CommentRequest {
    String userId;
    String postId;
    String postOwnerUsername;
    String username;
    String content;
}
