package org.spring.likeservice.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.likeservice.models.Emotion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDetailsDto {
	private String username;
	private String likeId;
	private Emotion emotion;
}
