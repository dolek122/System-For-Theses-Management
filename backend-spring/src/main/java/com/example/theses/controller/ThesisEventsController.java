package com.example.theses.controller;

import com.example.theses.service.ThesisEventService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/theses/{thesisId}/events")
public class ThesisEventsController {
	private final ThesisEventService eventService;

	public ThesisEventsController(ThesisEventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(@PathVariable String thesisId) {
		return eventService.subscribe(thesisId);
	}
}

