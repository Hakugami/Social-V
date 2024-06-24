package org.spring.commentservice;

import lombok.extern.slf4j.Slf4j;
import org.spring.commentservice.events.Notification;
import org.spring.commentservice.events.PostCreatedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class CommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentServiceApplication.class, args);
	}



}
