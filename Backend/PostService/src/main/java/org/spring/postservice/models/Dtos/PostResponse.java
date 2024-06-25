package org.spring.postservice.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
	String id;
	String userId;
	String username;
	String content;
	LocalDateTime createdAt;
	String profilePicture;
	LikeDto likes;
	List<CommentDto> comments;
	String videoUrl;
	List<String> imagesUrl;
	LocalDateTime updatedAt;
}
