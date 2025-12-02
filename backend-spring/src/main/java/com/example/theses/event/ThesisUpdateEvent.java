package com.example.theses.event;

import java.time.Instant;

public class ThesisUpdateEvent {
	private final String thesisId;
	private final String updateType; // "schedule_task", "document", "thesis"
	private final String entityId;
	private final String action; // "created", "updated", "deleted"
	private final Instant timestamp;

	public ThesisUpdateEvent(String thesisId, String updateType, String entityId, String action) {
		this.thesisId = thesisId;
		this.updateType = updateType;
		this.entityId = entityId;
		this.action = action;
		this.timestamp = Instant.now();
	}

	public String getThesisId() {
		return thesisId;
	}

	public String getUpdateType() {
		return updateType;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getAction() {
		return action;
	}

	public Instant getTimestamp() {
		return timestamp;
	}
}

