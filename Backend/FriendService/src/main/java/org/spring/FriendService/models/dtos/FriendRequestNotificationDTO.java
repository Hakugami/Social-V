package org.spring.FriendService.models.dtos;

import java.io.Serializable;

public record FriendRequestNotificationDTO(String id,String email, String firstname,String lastname, String image,String username) implements Serializable {
}