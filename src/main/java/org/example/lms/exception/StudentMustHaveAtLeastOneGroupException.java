package org.example.lms.exception;

public class StudentMustHaveAtLeastOneGroupException extends RuntimeException {
    public StudentMustHaveAtLeastOneGroupException(Long studentId, Long groupId) {
        super("Student " +
                studentId + " cannot be deleted from the group "
                + groupId + ", because it's his only group.");
    }
}
