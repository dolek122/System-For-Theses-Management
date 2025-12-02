package com.example.theses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ScheduleTaskCreateDTO {
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
	private String name;

	@NotBlank(message = "Scope is required")
	@Size(max = 2000, message = "Scope must not exceed 2000 characters")
	private String scope;

	@NotBlank(message = "Due date is required")
	private String dueDate; // ISO date string

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}

