package org.spring.notificationservice.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "notifications")
public class NotificationModel {
    @Id
    private String id;
    private String senderUsername;
    private String receiverUsername;
    private String targetId;
    private String message;
    private String notificationType;
}
