package org.spring.messageservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatMessage {
	@Id
	private String id;
	private String chatId;
	private String senderId;
	private String recipientId;
	private String content;
	private LocalDate timestamp;
}
