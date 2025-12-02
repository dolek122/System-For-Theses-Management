package com.example.theses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ThesisCreateDTO {
	@NotBlank(message = "Title is required")
	@Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
	private String title;

	@NotBlank(message = "Description is required")
	@Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
	private String description;

	@NotBlank(message = "Promoter ID is required")
	private String promoterId;

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

	public String getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(String promoterId) {
		this.promoterId = promoterId;
	}
}

