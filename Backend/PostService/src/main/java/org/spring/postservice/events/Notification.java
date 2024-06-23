package org.spring.postservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private String senderUsername;
    private String receiverUsername;
    private String message;
    private enum NotificationType {
        MESSAGE, FRIEND_REQUEST, LIKE, COMMENT
    }
}