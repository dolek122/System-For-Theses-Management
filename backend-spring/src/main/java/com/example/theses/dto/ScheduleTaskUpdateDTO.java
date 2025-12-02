package com.example.theses.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ScheduleTaskUpdateDTO {
	@Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
	private String name;

	@Size(max = 2000, message = "Scope must not exceed 2000 characters")
	private String scope;

	private String dueDate; // ISO date string

	@Pattern(regexp = "pending|in_review|completed", message = "Status must be one of: pending, in_review, completed")
	private String status;

	@Min(value = 1, message = "Grade must be at least 1")
	@Max(value = 5, message = "Grade must be at most 5")
	private Integer grade;

	@Size(max = 1000, message = "Comments must not exceed 1000 characters")
	private String comments;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}

