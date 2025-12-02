package com.example.theses.controller;

import com.example.theses.dto.StudentWithThesisDTO;
import com.example.theses.dto.UserCreateDTO;
import com.example.theses.dto.UserUpdateDTO;
import com.example.theses.exception.ResourceNotFoundException;
import com.example.theses.model.Thesis;
import com.example.theses.model.User;
import com.example.theses.repo.DocumentElementRepository;
import com.example.theses.repo.ScheduleTaskRepository;
import com.example.theses.repo.ThesisRepository;
import com.example.theses.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserRepository users;
	private final ThesisRepository theses;
	private final ScheduleTaskRepository tasks;
	private final DocumentElementRepository documents;

	public UserController(
		UserRepository users,
		ThesisRepository theses,
		ScheduleTaskRepository tasks,
		DocumentElementRepository documents
	) {
		this.users = users;
		this.theses = theses;
		this.tasks = tasks;
		this.documents = documents;
	}

	@GetMapping
	public List<User> list(@RequestParam(value = "role", required = false) String role) {
		if (role != null) return users.findByRole(role);
		return users.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable String id) {
		return users.findById(id)
			.map(ResponseEntity::ok)
			.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
	}

	@PostMapping
	public ResponseEntity<User> create(@Valid @RequestBody UserCreateDTO dto) {
		// Check if email already exists
		if (users.findByEmail(dto.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setRole(dto.getRole());
		user.setPassword(dto.getPassword());
		User saved = users.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable String id, @Valid @RequestBody UserUpdateDTO dto) {
		User user = users.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

		if (dto.getName() != null) user.setName(dto.getName());
		if (dto.getEmail() != null) {
			// Check if email is taken by another user
			if (users.findByEmail(dto.getEmail()).filter(u -> !u.getId().equals(id)).isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			user.setEmail(dto.getEmail());
		}
		if (dto.getRole() != null) user.setRole(dto.getRole());
		if (dto.getPassword() != null) user.setPassword(dto.getPassword()); // Hash in production!

		User saved = users.save(user);
		return ResponseEntity.ok(saved);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		if (!users.existsById(id)) {
			throw new ResourceNotFoundException("User with id " + id + " not found");
		}
		users.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/students/with-thesis")
	public List<StudentWithThesisDTO> getStudentsWithThesis(@RequestParam(value = "promoterId", required = false) String promoterId) {
		List<User> students = users.findByRole("student");
		
		return students.stream().map(student -> {
			StudentWithThesisDTO dto = new StudentWithThesisDTO();
			dto.setId(student.getId());
			dto.setName(student.getName());
			dto.setEmail(student.getEmail());

			// Find thesis for this student
			List<Thesis> studentTheses = theses.findAll().stream()
				.filter(t -> student.getId().equals(t.getStudentId()))
				.filter(t -> promoterId == null || promoterId.equals(t.getPromoterId()))
				.collect(Collectors.toList());

			if (!studentTheses.isEmpty()) {
				Thesis thesis = studentTheses.get(0);
				dto.setThesisId(thesis.getId());
				dto.setThesisTitle(thesis.getTitle());

				// Count tasks
				long tasksCount = tasks.findByThesisId(thesis.getId()).size();
				long completedTasksCount = tasks.findByThesisId(thesis.getId()).stream()
					.filter(t -> "completed".equals(t.getStatus()))
					.count();

				// Count documents
				long documentsCount = documents.findByThesisId(thesis.getId()).size();

				// Calculate average grade
				List<com.example.theses.model.ScheduleTask> allTasks = tasks.findByThesisId(thesis.getId());
				List<com.example.theses.model.DocumentElement> allDocs = documents.findByThesisId(thesis.getId());
				
				double totalGrade = 0;
				int gradeCount = 0;
				
				for (var task : allTasks) {
					if (task.getGrade() != null) {
						totalGrade += task.getGrade();
						gradeCount++;
					}
				}
				
				for (var doc : allDocs) {
					if (doc.getGrade() != null) {
						totalGrade += doc.getGrade();
						gradeCount++;
					}
				}

				dto.setTasksCount(tasksCount);
				dto.setCompletedTasksCount(completedTasksCount);
				dto.setDocumentsCount(documentsCount);
				dto.setAverageGrade(gradeCount > 0 ? Math.round((totalGrade / gradeCount) * 10.0) / 10.0 : null);
			}

			return dto;
		}).collect(Collectors.toList());
	}
}


