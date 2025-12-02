package com.example.theses.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConsultationSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false)
	private String promoterId;
	@Column(nullable = false)
	private String startTime; // ISO datetime string
	@Column(nullable = false)
	private String endTime; // ISO datetime string
	@Column(nullable = false)
	private Integer capacity;
	@ElementCollection
	private List<String> registeredStudentIds = new ArrayList<>();
	private String notes;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getPromoterId() { return promoterId; }
	public void setPromoterId(String promoterId) { this.promoterId = promoterId; }
	public String getStartTime() { return startTime; }
	public void setStartTime(String startTime) { this.startTime = startTime; }
	public String getEndTime() { return endTime; }
	public void setEndTime(String endTime) { this.endTime = endTime; }
	public Integer getCapacity() { return capacity; }
	public void setCapacity(Integer capacity) { this.capacity = capacity; }
	public List<String> getRegisteredStudentIds() { return registeredStudentIds; }
	public void setRegisteredStudentIds(List<String> registeredStudentIds) { this.registeredStudentIds = registeredStudentIds; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
}


