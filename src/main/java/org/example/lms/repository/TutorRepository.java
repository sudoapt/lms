package org.example.lms.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.lms.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

}
