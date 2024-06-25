package org.spring.postservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.postservice.models.enums.ContentType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {

	@MongoId
	private String id;
	private String userId;
	@CreatedBy
	private String username;
	private String content;
	private List<String> imageUrl;
	private String videoUrl;
	@CreatedDate
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private ContentType type;




}
