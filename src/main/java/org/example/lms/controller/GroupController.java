package org.example.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.GroupDto;
import org.example.lms.service.GroupService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/groups")
@Tag(name = "Groups", description = "Group management")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Get all the groups")
    @PageableAsQueryParam
    public ResponseEntity<Page<GroupDto>> getCourse(
            @Parameter(hidden = true)
            @PageableDefault(size = 2, sort = "id")
            Pageable pageable
    ) {
        Page<GroupDto> groupDtos = groupService.getGroups(pageable);
        return  ResponseEntity.ok(groupDtos);
    }

    @PostMapping
    @Operation(summary = "Create a new group")
    public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
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

    @PatchMapping("/{groupId}")
    @Operation(summary = "Edit a group")
    public ResponseEntity<GroupDto> editGroup(
            @PathVariable Long groupId,
            @RequestBody GroupDto groupDto) {
        GroupDto editedGroup = groupService.editGroup(groupId, groupDto);

        return ResponseEntity.ok(editedGroup);
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete a group")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long groupId
    ) {
        groupService.deleteGroup(groupId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
