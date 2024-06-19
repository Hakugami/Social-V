package org.spring.postservice.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "posts")
@Data
public class PostModel {

	@MongoId
	private String id;
	private String userId;
	private String username;
	private String content;
	private List<String> imageUrl;
	private String videoUrl;
	private String createdAt;
	private String type;




}
