package org.spring.postservice.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	String userId;
	String username;
	String content;
	String createdAt;
	Integer likes;
	List<CommentDto> comments;
	String videoUrl;
	List<String> imagesUrl;
}
