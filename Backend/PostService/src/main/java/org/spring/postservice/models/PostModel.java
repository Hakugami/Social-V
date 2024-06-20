package org.spring.postservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	private String username;
	private String content;
	private List<String> imageUrl;
	private String videoUrl;
	private LocalDateTime createdAt;
	private String type;




}
