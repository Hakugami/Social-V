package org.spring.notificationservice.services;

import lombok.RequiredArgsConstructor;
import org.spring.notificationservice.events.FriendRequestSentEvent;
import org.spring.notificationservice.events.Notification;
import org.spring.notificationservice.events.PostCreatedEvent;
import org.spring.notificationservice.models.NotificationModel;
import org.spring.notificationservice.models.dtos.NotificationDto;
import org.spring.notificationservice.repositories.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "notifications-topic", groupId = "notification-service")
    public void listen(Notification notification) {
        NotificationModel notificationModel = NotificationModel.builder()
                .senderUsername(notification.getSenderUsername())
                .receiverUsername(notification.getReceiverUsername())
                .targetId(notification.getId())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .build();
        notificationRepository.save(notificationModel);

        messagingTemplate.convertAndSendToUser(notification.getReceiverUsername(), "/queue/notifications", notification);
    }


    public List<NotificationDto> findNotificationByReceiverUsername(String username) {
        List<NotificationModel> notificationModels = notificationRepository.findByReceiverUsername(username);
        return notificationModels.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private NotificationDto mapToDto(NotificationModel notificationModel) {
        return NotificationDto.builder()
                .id(notificationModel.getTargetId())
                .senderUsername(notificationModel.getSenderUsername())
                .receiverUsername(notificationModel.getReceiverUsername())
                .message(notificationModel.getMessage())
                .notificationType(notificationModel.getNotificationType())
                .build();

    }


}