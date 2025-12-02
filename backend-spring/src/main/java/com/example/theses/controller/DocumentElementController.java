package com.example.theses.controller;

import com.example.theses.dto.DocumentElementCreateDTO;
import com.example.theses.dto.DocumentElementUpdateDTO;
import com.example.theses.event.ThesisUpdateEvent;
import com.example.theses.exception.ResourceNotFoundException;
import com.example.theses.model.DocumentElement;
import com.example.theses.repo.DocumentElementRepository;
import com.example.theses.service.ThesisEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/theses/{thesisId}/documents")
@Transactional
public class DocumentElementController {
	private final DocumentElementRepository docs;
	private final ThesisEventService eventService;

	public DocumentElementController(DocumentElementRepository docs, ThesisEventService eventService) {
		this.docs = docs;
		this.eventService = eventService;
	}

	@GetMapping
	public List<DocumentElement> list(@PathVariable String thesisId) {
		return docs.findByThesisId(thesisId);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public DocumentElement create(
			@PathVariable String thesisId,
			@RequestPart("data") @Valid DocumentElementCreateDTO dto,
			@RequestPart(value = "file", required = false) MultipartFile file
	) throws IOException {
		DocumentElement d = new DocumentElement();
		d.setThesisId(thesisId);
		d.setType(dto.getType() != null ? dto.getType() : "chapter");
		d.setTitle(dto.getTitle());
		d.setContent(dto.getContent() != null ? dto.getContent() : "");
		if (dto.getOrder() != null) d.setOrdinalValue(dto.getOrder());
		d.setStatus("draft");
		d.setUpdatedAt(Instant.now().toString());

		if (file != null && !file.isEmpty()) {
			d.setFileName(file.getOriginalFilename());
			d.setFileType(file.getContentType());
			d.setFileData(file.getBytes());
		}

		DocumentElement saved = docs.save(d);
		eventService.publishEvent(new ThesisUpdateEvent(thesisId, "document", saved.getId(), "created"));
		return saved;
	}

	@GetMapping("/{docId}/download")
	public ResponseEntity<byte[]> download(@PathVariable String thesisId, @PathVariable String docId) {
		DocumentElement doc = docs.findById(docId)
			.filter(d -> thesisId.equals(d.getThesisId()))
			.orElseThrow(() -> new ResourceNotFoundException("Document with id " + docId + " not found for thesis " + thesisId));

		if (doc.getFileData() == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
			.contentType(MediaType.parseMediaType(doc.getFileType() != null ? doc.getFileType() : MediaType.APPLICATION_OCTET_STREAM_VALUE))
			.body(doc.getFileData());
	}

	@PatchMapping("/{docId}")
	public ResponseEntity<DocumentElement> patch(@PathVariable String thesisId, @PathVariable String docId, @Valid @RequestBody DocumentElementUpdateDTO dto) {
		DocumentElement doc = docs.findById(docId)
			.filter(d -> thesisId.equals(d.getThesisId()))
			.orElseThrow(() -> new ResourceNotFoundException("Document with id " + docId + " not found for thesis " + thesisId));

		if (dto.getTitle() != null) doc.setTitle(dto.getTitle());
		if (dto.getContent() != null) doc.setContent(dto.getContent());
		if (dto.getStatus() != null) doc.setStatus(dto.getStatus());
		if (dto.getComments() != null) doc.setComments(dto.getComments());
		if (dto.getGrade() != null) doc.setGrade(dto.getGrade());
		doc.setUpdatedAt(Instant.now().toString());

		DocumentElement saved = docs.save(doc);
		eventService.publishEvent(new ThesisUpdateEvent(thesisId, "document", saved.getId(), "updated"));
		return ResponseEntity.ok(saved);
	}

	@DeleteMapping("/{docId}")
	public ResponseEntity<Void> remove(@PathVariable String thesisId, @PathVariable String docId) {
		docs.findById(docId)
			.filter(d -> thesisId.equals(d.getThesisId()))
			.orElseThrow(() -> new ResourceNotFoundException("Document with id " + docId + " not found for thesis " + thesisId));
		docs.deleteById(docId);
		eventService.publishEvent(new ThesisUpdateEvent(thesisId, "document", docId, "deleted"));
		return ResponseEntity.noContent().build();
	}
}
