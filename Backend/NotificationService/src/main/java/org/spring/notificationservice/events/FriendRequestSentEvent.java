package org.spring.notificationservice.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestSentEvent {
    private String senderId;
    private String receiverId;
}
