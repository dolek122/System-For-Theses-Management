package com.example.theses.repo;

import com.example.theses.model.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, String> {
	List<ScheduleTask> findByThesisId(String thesisId);
}


