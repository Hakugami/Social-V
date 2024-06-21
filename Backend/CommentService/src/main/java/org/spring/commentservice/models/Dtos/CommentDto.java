package org.spring.commentservice.models.Dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDto implements Serializable {
    String id;
    String userId;
    String username;
    String content;
}