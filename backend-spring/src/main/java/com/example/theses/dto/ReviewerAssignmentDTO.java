package com.example.theses.dto;

import jakarta.validation.constraints.NotNull;

public class ReviewerAssignmentDTO {
    @NotNull
    private String reviewerId;

    public String getReviewerId() { return reviewerId; }
    public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }
}

