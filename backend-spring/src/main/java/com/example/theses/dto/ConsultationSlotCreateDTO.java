package com.example.theses.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ConsultationSlotCreateDTO {
	@NotBlank(message = "Start time is required")
	private String startTime; // ISO datetime string

	@NotBlank(message = "End time is required")
	private String endTime; // ISO datetime string

	@NotNull(message = "Capacity is required")
	@Min(value = 1, message = "Capacity must be at least 1")
	private Integer capacity;

	@Size(max = 500, message = "Notes must not exceed 500 characters")
	private String notes;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}

