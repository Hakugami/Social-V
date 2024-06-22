package org.spring.FriendService.models.dtos;

import java.io.Serializable;

public record FriendRequestDTO(String recipientId, String requesterId) implements Serializable {
}