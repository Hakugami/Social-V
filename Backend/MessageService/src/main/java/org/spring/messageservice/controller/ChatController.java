package org.spring.messageservice.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.spring.messageservice.model.ChatMessage;
import org.spring.messageservice.model.ChatNotification;
import org.spring.messageservice.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    @ApiResponse(description = "send a chat message",responseCode = "200")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMessage = chatMessageService.save(chatMessage);

        simpMessagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(),
                "/queue/messages",
                ChatNotification.builder()
                        .id(savedMessage.getId())
                        .senderId(savedMessage.getSenderId())
                        .recipientId(savedMessage.getRecipientId())
                        .content(savedMessage.getContent())
                        .build());
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    @ApiResponse(description = "get all the chat messages between two users",responseCode = "200")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable("senderId") String senderId,
                                                             @PathVariable("recipientId")  String recipientId)
    {
        return ResponseEntity.ok(chatMessageService.getChatMessages(senderId, recipientId));
    }
}
