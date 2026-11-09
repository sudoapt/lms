package org.example.lms.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.lms.model.Schedule;
import org.example.lms.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {
}
