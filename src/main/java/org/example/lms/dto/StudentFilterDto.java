package org.example.lms.dto;

import java.util.List;

public record StudentFilterDto(
        String studentFilterFirstName,
        String studentFilerLastName,
        List<Long> groupsIDs,
        List<Long> coursesIDs
) {
    public boolean isEmpty() {
        return studentFilterFirstName == null
                && studentFilerLastName == null
                && groupsIDs.isEmpty()
                && coursesIDs.isEmpty();
    }
}
