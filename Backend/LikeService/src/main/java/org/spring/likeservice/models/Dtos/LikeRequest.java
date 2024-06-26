package org.spring.likeservice.models.Dtos;

import org.spring.likeservice.models.Emotion;

import java.io.Serializable;

public record LikeRequest(String postId, String userId, String username ,String postOwnerUsername, Emotion emotion) implements Serializable {
}
