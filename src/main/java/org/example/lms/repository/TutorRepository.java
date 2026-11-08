package org.example.lms.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.lms.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    default Tutor getOrThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException("No tutor with the id: " + id + " is found"));
    }
}
