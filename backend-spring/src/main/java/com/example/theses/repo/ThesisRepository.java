package com.example.theses.repo;

import com.example.theses.model.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, String> {
	List<Thesis> findByPromoterId(String promoterId);
	List<Thesis> findByStudentId(String studentId);
}


