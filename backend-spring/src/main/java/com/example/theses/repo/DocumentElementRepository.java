package com.example.theses.repo;

import com.example.theses.model.DocumentElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentElementRepository extends JpaRepository<DocumentElement, String> {
	List<DocumentElement> findByThesisId(String thesisId);
}


