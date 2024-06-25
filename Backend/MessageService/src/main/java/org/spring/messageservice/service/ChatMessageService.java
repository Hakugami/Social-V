package org.spring.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.spring.messageservice.model.ChatMessage;
import org.spring.messageservice.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        String chatId = chatRoomService.getChatRoomId(chatMessage.getSenderId(),
                        chatMessage.getRecipientId(),
                        true)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        chatMessage.setChatId(chatId);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(String senderId, String recipientId) {
        // this method is used to get all chat messages between two users
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());
    }
}
