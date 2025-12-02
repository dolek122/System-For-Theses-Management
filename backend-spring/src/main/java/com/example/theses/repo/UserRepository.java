package com.example.theses.repo;

import com.example.theses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	List<User> findByRole(String role);
	Optional<User> findByEmail(String email);
}


