package com.example.theses.repo;

import com.example.theses.model.ConsultationSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationSlotRepository extends JpaRepository<ConsultationSlot, String> {
	List<ConsultationSlot> findByPromoterId(String promoterId);
}


