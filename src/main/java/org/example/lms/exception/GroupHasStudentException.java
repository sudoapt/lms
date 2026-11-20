package org.example.lms.exception;

public class GroupHasStudentException extends RuntimeException {
    public GroupHasStudentException(Long groupId) {
        super("Group " + groupId + " cannot be deleted: not empty.");
    }
}
