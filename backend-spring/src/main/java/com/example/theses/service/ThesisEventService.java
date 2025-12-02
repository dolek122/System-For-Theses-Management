package com.example.theses.service;

import com.example.theses.event.ThesisUpdateEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ThesisEventService {
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final SimpMessagingTemplate messagingTemplate;

	public ThesisEventService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public SseEmitter subscribe(String thesisId) {
		String clientId = thesisId + "_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

		emitter.onCompletion(() -> emitters.remove(clientId));
		emitter.onTimeout(() -> {
			emitters.remove(clientId);
			emitter.complete();
		});
		emitter.onError((ex) -> {
			emitters.remove(clientId);
			emitter.completeWithError(ex);
		});

		emitters.put(clientId, emitter);

		try {
			emitter.send(SseEmitter.event()
				.name("connected")
				.data(Map.of("thesisId", thesisId, "message", "Connected to updates stream")));
		} catch (IOException e) {
			emitters.remove(clientId);
			emitter.completeWithError(e);
		}

		return emitter;
	}

	public void publishEvent(ThesisUpdateEvent event) {
		emitters.entrySet().removeIf(entry -> {
			String clientId = entry.getKey();
			SseEmitter emitter = entry.getValue();

			if (clientId.startsWith(event.getThesisId() + "_")) {
				try {
					emitter.send(SseEmitter.event()
						.name("update")
						.data(Map.of(
							"thesisId", event.getThesisId(),
							"updateType", event.getUpdateType(),
							"entityId", event.getEntityId(),
							"action", event.getAction(),
							"timestamp", event.getTimestamp().toString()
						)));
					return false;
				} catch (IOException e) {
					return true;
				}
			}
			return false;
		});

		messagingTemplate.convertAndSend("/topic/thesis/" + event.getThesisId() + "/events", event);
	}
}
