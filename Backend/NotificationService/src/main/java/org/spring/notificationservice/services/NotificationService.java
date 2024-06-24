package org.spring.notificationservice.services;

import lombok.RequiredArgsConstructor;
import org.spring.notificationservice.events.FriendRequestSentEvent;
import org.spring.notificationservice.events.PostCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;


}