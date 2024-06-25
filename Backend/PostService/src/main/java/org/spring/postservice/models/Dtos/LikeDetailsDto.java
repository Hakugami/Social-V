package org.spring.postservice.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.postservice.models.enums.Emotion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDetailsDto {
	private String username;
	private String likeId;
	private Emotion emotion;
}
