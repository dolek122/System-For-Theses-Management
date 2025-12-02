package com.example.theses.controller;

import com.example.theses.model.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	@MessageMapping("/chat/{thesisId}/sendMessage")
	@SendTo("/topic/thesis/{thesisId}/chat")
	public ChatMessage sendMessage(@DestinationVariable String thesisId, @Payload ChatMessage chatMessage) {
		chatMessage.setTimestamp(java.time.Instant.now().toString());
		return chatMessage;
	}

	@MessageMapping("/chat/{thesisId}/addUser")
	@SendTo("/topic/thesis/{thesisId}/chat")
	public ChatMessage addUser(@DestinationVariable String thesisId, @Payload ChatMessage chatMessage) {
		return chatMessage;
	}
}

