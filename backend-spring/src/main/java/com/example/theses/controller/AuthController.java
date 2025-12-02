package com.example.theses.controller;

import com.example.theses.dto.LoginDTO;
import com.example.theses.dto.UserCreateDTO;
import com.example.theses.model.User;
import com.example.theses.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final UserRepository userRepository;

	public AuthController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@Valid @RequestBody UserCreateDTO dto) {
        logger.info("Registering user: {}", dto.getEmail());
        try {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                logger.warn("Email already exists: {}", dto.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            User user = new User();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setRole(dto.getRole());
            user.setPassword(dto.getPassword()); // In production, use password hashing!

            User saved = userRepository.save(user);
            logger.info("User created: {}", saved.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            logger.error("Error registering user", e);
            throw e; // Let Spring handle it or return 500 explicitly
        }
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@Valid @RequestBody LoginDTO dto) {
        logger.info("Login attempt: {}", dto.getEmail());
		Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getPassword().equals(dto.getPassword())) {
                logger.info("Login successful: {}", user.getId());
				return ResponseEntity.ok(user);
			}
		}
        logger.warn("Login failed for: {}", dto.getEmail());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
