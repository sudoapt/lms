package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.GroupDto;
import org.example.lms.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/group")
@Tag(name = "Groups", description = "Group management")
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "Create a new group")
    public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto) {
       GroupDto createdGroup =  groupService.createGroup(groupDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping("/{groupId}/courses/{courseId}")
    @Operation(summary = "Assign group on a course")
    public ResponseEntity<Void> assignGroupOnCourse(
            @PathVariable Long groupId,
            @PathVariable Long courseId
    ) {
        groupService.assignGroupOnCourse(groupId, courseId);
        return ResponseEntity.noContent().build();
    }
}
