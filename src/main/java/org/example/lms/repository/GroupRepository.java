package org.example.lms.repository;

import jakarta.persistence.EntityNotFoundException;
import org.example.lms.model.Group;
import org.example.lms.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByGroupName(String groupName);

    default Group getOrThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException("No group with the id: " + id + " is found"));
    }
}
