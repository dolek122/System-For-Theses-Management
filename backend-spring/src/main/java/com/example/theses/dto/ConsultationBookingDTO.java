package com.example.theses.dto;

import jakarta.validation.constraints.NotBlank;

public class ConsultationBookingDTO {
	@NotBlank(message = "Student ID is required")
	private String studentId;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
}

