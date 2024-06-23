package org.spring.notificationservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.spring.notificationservice.events.PostCreatedEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@Slf4j
public class NotificationController {
    @MessageMapping("/public.notifications")
    @SendTo("/topic/public")
    public String postCreated(@Payload String userName) throws Exception {
        log.info("User {} logged in successfully " , userName);
        return "User " + userName + " logged in successfully";
    }
}
