package org.spring.postservice.models.Dtos;

import java.io.Serializable;

public class CommentDto implements Serializable {
	String commentId;
	String userId;
	String username;
	String content;
}
