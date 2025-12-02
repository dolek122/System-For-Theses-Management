package com.example.theses.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Thesis {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String promoterId;
	private String studentId;
	private String reviewerId;
	@Column(nullable = false)
	private Instant createdAt = Instant.now();
	@Column(nullable = false)
	private Instant updatedAt = Instant.now();

	@PreUpdate
	public void onUpdate() { this.updatedAt = Instant.now(); }

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getPromoterId() { return promoterId; }
	public void setPromoterId(String promoterId) { this.promoterId = promoterId; }
	public String getStudentId() { return studentId; }
	public void setStudentId(String studentId) { this.studentId = studentId; }
	public String getReviewerId() { return reviewerId; }
	public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


