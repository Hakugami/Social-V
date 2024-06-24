package org.spring.notificationservice;

import lombok.RequiredArgsConstructor;
import org.spring.notificationservice.events.PostCreatedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;

@SpringBootApplication
@RequiredArgsConstructor
public class NotificationServiceApplication {
    private final SimpMessagingTemplate messagingTemplate;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }



}
