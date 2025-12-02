package com.example.theses.controller;

import com.example.theses.dto.StudentAssignmentDTO;
import com.example.theses.dto.ReviewerAssignmentDTO;
import com.example.theses.dto.ThesisCreateDTO;
import com.example.theses.dto.ThesisUpdateDTO;
import com.example.theses.exception.ResourceNotFoundException;
import com.example.theses.model.Thesis;
import com.example.theses.repo.ThesisRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/theses")
public class ThesisController {
    private static final Logger logger = LoggerFactory.getLogger(ThesisController.class);
	private final ThesisRepository theses;
	public ThesisController(ThesisRepository theses) { this.theses = theses; }

	@GetMapping
	public List<Thesis> list() { return theses.findAll(); }

	@PostMapping
	public Thesis create(@Valid @RequestBody ThesisCreateDTO dto) {
        logger.info("Creating thesis for promoter: {}", dto.getPromoterId());
		Thesis t = new Thesis();
		t.setTitle(dto.getTitle());
		t.setDescription(dto.getDescription());
		t.setPromoterId(dto.getPromoterId());
		return theses.save(t);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Thesis> update(@PathVariable String id, @Valid @RequestBody ThesisUpdateDTO dto) {
		Thesis thesis = theses.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Thesis with id " + id + " not found"));

		if (dto.getTitle() != null) thesis.setTitle(dto.getTitle());
		if (dto.getDescription() != null) thesis.setDescription(dto.getDescription());

		return ResponseEntity.ok(theses.save(thesis));
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<Thesis> getByStudentId(@PathVariable String studentId) {
		List<Thesis> list = theses.findByStudentId(studentId);
		if (list.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(list.get(0));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(@PathVariable String id) {
		if (!theses.existsById(id)) {
			throw new ResourceNotFoundException("Thesis with id " + id + " not found");
		}
		theses.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/student")
	public ResponseEntity<Thesis> setStudent(@PathVariable String id, @Valid @RequestBody StudentAssignmentDTO dto) {
		Thesis thesis = theses.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Thesis with id " + id + " not found"));

		if (dto.getStudentId() != null) {
			// Check if student is already assigned to another thesis
			List<Thesis> existing = theses.findByStudentId(dto.getStudentId());
			if (!existing.isEmpty() && !existing.get(0).getId().equals(id)) {
				return ResponseEntity.status(409).build(); // Conflict
			}
		}

		thesis.setStudentId(dto.getStudentId());
		return ResponseEntity.ok(theses.save(thesis));
	}

	@PatchMapping("/{id}/reviewer")
	public ResponseEntity<Thesis> setReviewer(@PathVariable String id, @Valid @RequestBody ReviewerAssignmentDTO dto) {
		Thesis thesis = theses.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Thesis with id " + id + " not found"));
		thesis.setReviewerId(dto.getReviewerId());
		return ResponseEntity.ok(theses.save(thesis));
	}
}
