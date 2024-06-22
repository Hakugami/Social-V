package org.spring.FriendService.models.dtos;

import java.io.Serializable;

public record FriendRequestNotificationDTO(String id,String email, String name, String image) implements Serializable {
}