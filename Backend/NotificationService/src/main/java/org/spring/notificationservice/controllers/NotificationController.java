package org.spring.notificationservice.controllers;

import org.spring.notificationservice.events.PostCreatedEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @MessageMapping("/user.notifications")
    @SendTo("/user/notifications")
    public PostCreatedEvent postCreated(PostCreatedEvent postCreatedEvent) throws Exception {
        // Simulate delay
        Thread.sleep(1000);
        return postCreatedEvent;
    }
}
