package com.example.theses.model;

import jakarta.persistence.*;

@Entity
public class DocumentElement {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false)
	private String thesisId;
	@Column(nullable = false)
	private String type; // toc | chapter | bibliography
	@Column(nullable = false)
	private String title;
	@Column(nullable = false, length = 10000)
	private String content;

	private String fileName;
	private String fileType;
	@Lob
	private byte[] fileData;

	private Integer ordinalValue;
	@Column(nullable = false)
	private String status; // draft | submitted | reviewed
	private String comments;
	private Integer grade;
	@Column(nullable = false)
	private String updatedAt; // ISO date string

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getThesisId() { return thesisId; }
	public void setThesisId(String thesisId) { this.thesisId = thesisId; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	public String getFileType() { return fileType; }
	public void setFileType(String fileType) { this.fileType = fileType; }
	public byte[] getFileData() { return fileData; }
	public void setFileData(byte[] fileData) { this.fileData = fileData; }
	public Integer getOrdinalValue() { return ordinalValue; }
	public void setOrdinalValue(Integer ordinalValue) { this.ordinalValue = ordinalValue; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getComments() { return comments; }
	public void setComments(String comments) { this.comments = comments; }
	public Integer getGrade() { return grade; }
	public void setGrade(Integer grade) { this.grade = grade; }
	public String getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}


