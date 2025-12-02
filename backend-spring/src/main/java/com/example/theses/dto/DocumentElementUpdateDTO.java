package com.example.theses.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DocumentElementUpdateDTO {
	@Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
	private String title;

	@Size(max = 10000, message = "Content must not exceed 10000 characters")
	private String content;

	@Pattern(regexp = "draft|submitted|reviewed", message = "Status must be one of: draft, submitted, reviewed")
	private String status;

	@Size(max = 1000, message = "Comments must not exceed 1000 characters")
	private String comments;

	@Min(value = 1, message = "Grade must be at least 1")
	@Max(value = 5, message = "Grade must be at most 5")
	private Integer grade;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}

