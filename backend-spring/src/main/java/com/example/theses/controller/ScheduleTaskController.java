package com.example.theses.controller;

import com.example.theses.dto.ScheduleTaskCreateDTO;
import com.example.theses.dto.ScheduleTaskUpdateDTO;
import com.example.theses.event.ThesisUpdateEvent;
import com.example.theses.exception.ResourceNotFoundException;
import com.example.theses.model.ScheduleTask;
import com.example.theses.repo.ScheduleTaskRepository;
import com.example.theses.service.ThesisEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/theses/{thesisId}/schedule-tasks")
public class ScheduleTaskController {
	private final ScheduleTaskRepository tasks;
	private final ThesisEventService eventService;

	public ScheduleTaskController(ScheduleTaskRepository tasks, ThesisEventService eventService) {
		this.tasks = tasks;
		this.eventService = eventService;
	}

	@GetMapping
	public List<ScheduleTask> list(@PathVariable String thesisId) {
		return tasks.findByThesisId(thesisId);
	}

	@GetMapping(value = "/export", produces = "text/csv")
	public ResponseEntity<byte[]> exportTasks(@PathVariable String thesisId) {
		List<ScheduleTask> taskList = tasks.findByThesisId(thesisId);

		StringBuilder csv = new StringBuilder();
		csv.append("Nazwa,Zakres,Termin,Status,Ocena\n");

		for (ScheduleTask task : taskList) {
			csv.append(escapeSpecialCharacters(task.getName())).append(",");
			csv.append(escapeSpecialCharacters(task.getScope())).append(",");
			csv.append(task.getDueDate()).append(",");
			csv.append(task.getStatus()).append(",");
			csv.append(task.getGrade() != null ? task.getGrade() : "").append("\n");
		}

		byte[] csvBytes = csv.toString().getBytes(StandardCharsets.UTF_8);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", "harmonogram.csv");

		return ResponseEntity.ok()
			.headers(headers)
			.contentType(MediaType.parseMediaType("text/csv"))
			.body(csvBytes);
	}

	private String escapeSpecialCharacters(String data) {
		if (data == null) return "";
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

	@PostMapping
	public ScheduleTask create(@PathVariable String thesisId, @Valid @RequestBody ScheduleTaskCreateDTO dto) {
		ScheduleTask t = new ScheduleTask();
		t.setThesisId(thesisId);
		t.setName(dto.getName());
		t.setScope(dto.getScope());
		t.setDueDate(dto.getDueDate());
		t.setStatus("pending");
		ScheduleTask saved = tasks.save(t);
		eventService.publishEvent(new ThesisUpdateEvent(thesisId, "schedule_task", saved.getId(), "created"));
		return saved;
	}

	@PatchMapping("/{taskId}")
	public ResponseEntity<ScheduleTask> patch(@PathVariable String thesisId, @PathVariable String taskId, @Valid @RequestBody ScheduleTaskUpdateDTO dto) {
		ScheduleTask task = tasks.findById(taskId)
			.filter(t -> thesisId.equals(t.getThesisId()))
			.orElseThrow(() -> new ResourceNotFoundException("Schedule task with id " + taskId + " not found for thesis " + thesisId));

		if (dto.getName() != null) task.setName(dto.getName());
		if (dto.getScope() != null) task.setScope(dto.getScope());
		if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());
		if (dto.getStatus() != null) task.setStatus(dto.getStatus());
		if (dto.getGrade() != null) task.setGrade(dto.getGrade());
		if (dto.getComments() != null) task.setComments(dto.getComments());

		ScheduleTask saved = tasks.save(task);
		eventService.publishEvent(new ThesisUpdateEvent(thesisId, "schedule_task", saved.getId(), "updated"));
		return ResponseEntity.ok(saved);
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> remove(@PathVariable String thesisId, @PathVariable String taskId) {
		tasks.findById(taskId)
			.filter(t -> thesisId.equals(t.getThesisId()))
			.orElseThrow(() -> new ResourceNotFoundException("Schedule task with id " + taskId + " not found for thesis " + thesisId));
		tasks.deleteById(taskId);
		eventService.publishEvent(new ThesisUpdateEvent(thesisId, "schedule_task", taskId, "deleted"));
		return ResponseEntity.noContent().build();
	}
}


