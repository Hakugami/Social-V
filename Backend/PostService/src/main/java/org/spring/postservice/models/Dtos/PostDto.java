package org.spring.postservice.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto implements Serializable {
	String userId;
	String username;
	String content;
	String createdAt;
}
