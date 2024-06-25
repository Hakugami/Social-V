package org.spring.notificationservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private String id;
    private String senderUsername;
    private String receiverUsername;
    private String message;
    private String notificationType; // e.g. "LIKE", "COMMENT"
}
