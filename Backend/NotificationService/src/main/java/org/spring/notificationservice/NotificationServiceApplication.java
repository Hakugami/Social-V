package org.spring.notificationservice;

import lombok.RequiredArgsConstructor;
import org.spring.notificationservice.events.PostCreatedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class NotificationServiceApplication {
    private final SimpMessagingTemplate messagingTemplate;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "post-topic", groupId = "notification-service")
    public void listen(String username) {
        System.out.println("Received event: " + username);

        messagingTemplate.convertAndSendToUser(username, "/queue/messages", "post created by this user" + username);
        System.out.println("Message sent to user: " + username);

    }

}
