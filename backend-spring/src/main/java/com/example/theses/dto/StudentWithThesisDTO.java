package com.example.theses.dto;

public class StudentWithThesisDTO {
	private String id;
	private String name;
	private String email;
	private String thesisId;
	private String thesisTitle;
	private Long tasksCount;
	private Long completedTasksCount;
	private Long documentsCount;
	private Double averageGrade;

	public StudentWithThesisDTO() {}

	public StudentWithThesisDTO(String id, String name, String email, String thesisId, String thesisTitle) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.thesisId = thesisId;
		this.thesisTitle = thesisTitle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getThesisId() {
		return thesisId;
	}

	public void setThesisId(String thesisId) {
		this.thesisId = thesisId;
	}

	public String getThesisTitle() {
		return thesisTitle;
	}

	public void setThesisTitle(String thesisTitle) {
		this.thesisTitle = thesisTitle;
	}

	public Long getTasksCount() {
		return tasksCount;
	}

	public void setTasksCount(Long tasksCount) {
		this.tasksCount = tasksCount;
	}

	public Long getCompletedTasksCount() {
		return completedTasksCount;
	}

	public void setCompletedTasksCount(Long completedTasksCount) {
		this.completedTasksCount = completedTasksCount;
	}

	public Long getDocumentsCount() {
		return documentsCount;
	}

	public void setDocumentsCount(Long documentsCount) {
		this.documentsCount = documentsCount;
	}

	public Double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(Double averageGrade) {
		this.averageGrade = averageGrade;
	}
}

