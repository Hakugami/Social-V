package org.spring.notificationservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.notificationservice.events.PostCreatedEvent;
import org.spring.notificationservice.models.NotificationModel;
import org.spring.notificationservice.models.dtos.NotificationDto;
import org.spring.notificationservice.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j

public class NotificationController {
    private final NotificationService notificationService;
    @MessageMapping("/public.notifications")
    @SendTo("/topic/public")
    public String postCreated(@Payload String userName) throws Exception {
        log.info("User {} logged in successfully " , userName);
        return "User " + userName + " logged in successfully";
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<NotificationDto>> getUserNotifications(@PathVariable String username) {
        return ResponseEntity.ok(notificationService.findNotificationByReceiverUsername(username));

    }




}
