package com.example.theses.model;

import jakarta.persistence.*;

@Entity
public class ScheduleTask {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false)
	private String thesisId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, length = 2000)
	private String scope;
	@Column(nullable = false)
	private String dueDate; // ISO date string
	@Column(nullable = false)
	private String status; // pending | in_review | completed
	private Integer grade;
	private String comments;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getThesisId() { return thesisId; }
	public void setThesisId(String thesisId) { this.thesisId = thesisId; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getScope() { return scope; }
	public void setScope(String scope) { this.scope = scope; }
	public String getDueDate() { return dueDate; }
	public void setDueDate(String dueDate) { this.dueDate = dueDate; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public Integer getGrade() { return grade; }
	public void setGrade(Integer grade) { this.grade = grade; }
	public String getComments() { return comments; }
	public void setComments(String comments) { this.comments = comments; }
}


