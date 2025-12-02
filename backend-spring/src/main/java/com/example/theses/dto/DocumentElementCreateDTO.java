package com.example.theses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DocumentElementCreateDTO {
	@Pattern(regexp = "toc|chapter|bibliography", message = "Type must be one of: toc, chapter, bibliography")
	private String type = "chapter";

	@NotBlank(message = "Title is required")
	@Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
	private String title;

	@Size(max = 10000, message = "Content must not exceed 10000 characters")
	private String content;

	private Integer order;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
