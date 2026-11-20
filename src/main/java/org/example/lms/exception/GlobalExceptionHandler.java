package org.example.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExistsException(AlreadyExistsException ex) {
        Map<String, Object> error = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", "Data integrity violation",
                        "message", error
                )
        );
    }

    @ExceptionHandler(GroupHasStudentException.class)
    public ResponseEntity<Map<String, Object>> handleGroupHasStudentException(GroupHasStudentException ex) {
        String error = "Database integrity error: this group is not empty, so it cannot be deleted.";
        if (ex.getMessage() != null && ex.getMessage().contains("student_groups_groups_id_fk")) {
            error = "Cannot delete this group because students are still enrolled in it.";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", "Data integrity violation",
                        "message", error
                )
        );
    }

    @ExceptionHandler(StudentMustHaveAtLeastOneGroupException.class)
    public ResponseEntity<Map<String, Object>> handleStudentMustHaveAtLeastOneGroupException(StudentMustHaveAtLeastOneGroupException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", "Business logic violation",
                        "message", ex
                )
        );
    }
}

