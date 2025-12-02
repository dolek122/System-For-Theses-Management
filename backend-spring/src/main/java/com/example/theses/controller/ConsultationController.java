package com.example.theses.controller;

import com.example.theses.dto.ConsultationBookingDTO;
import com.example.theses.dto.ConsultationSlotCreateDTO;
import com.example.theses.exception.ResourceNotFoundException;
import com.example.theses.model.ConsultationSlot;
import com.example.theses.repo.ConsultationSlotRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultationController {
	private final ConsultationSlotRepository slots;
	public ConsultationController(ConsultationSlotRepository slots) { this.slots = slots; }

	@GetMapping("/api/promoters/{promoterId}/consultations")
	public List<ConsultationSlot> list(@PathVariable String promoterId) {
		return slots.findByPromoterId(promoterId);
	}

	@PostMapping("/api/promoters/{promoterId}/consultations")
	public ConsultationSlot create(@PathVariable String promoterId, @Valid @RequestBody ConsultationSlotCreateDTO dto) {
		ConsultationSlot s = new ConsultationSlot();
		s.setPromoterId(promoterId);
		s.setStartTime(dto.getStartTime());
		s.setEndTime(dto.getEndTime());
		s.setCapacity(dto.getCapacity());
		s.setNotes(dto.getNotes());
		return slots.save(s);
	}

	@DeleteMapping("/api/promoters/{promoterId}/consultations/{slotId}")
	public ResponseEntity<Void> remove(@PathVariable String promoterId, @PathVariable String slotId) {
		slots.findById(slotId)
			.filter(s -> promoterId.equals(s.getPromoterId()))
			.orElseThrow(() -> new ResourceNotFoundException("Consultation slot with id " + slotId + " not found for promoter " + promoterId));
		slots.deleteById(slotId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/api/consultations/{slotId}/book")
	public ResponseEntity<Void> book(@PathVariable String slotId, @Valid @RequestBody ConsultationBookingDTO dto) {
		ConsultationSlot slot = slots.findById(slotId)
			.orElseThrow(() -> new ResourceNotFoundException("Consultation slot with id " + slotId + " not found"));

		if (slot.getRegisteredStudentIds().size() >= slot.getCapacity()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (slot.getRegisteredStudentIds().contains(dto.getStudentId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		slot.getRegisteredStudentIds().add(dto.getStudentId());
		slots.save(slot);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/api/consultations/{slotId}/cancel")
	public ResponseEntity<Void> cancel(@PathVariable String slotId, @Valid @RequestBody ConsultationBookingDTO dto) {
		ConsultationSlot slot = slots.findById(slotId)
			.orElseThrow(() -> new ResourceNotFoundException("Consultation slot with id " + slotId + " not found"));

		if (!slot.getRegisteredStudentIds().remove(dto.getStudentId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		slots.save(slot);
		return ResponseEntity.noContent().build();
	}
}


