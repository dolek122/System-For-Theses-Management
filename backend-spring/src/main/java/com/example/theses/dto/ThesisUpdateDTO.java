package com.example.theses.dto;

import jakarta.validation.constraints.Size;

public class ThesisUpdateDTO {
	@Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
	private String title;

	@Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

