package org.example.lms.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.GroupDto;
import org.example.lms.exception.AlreadyExistsException;
import org.example.lms.mapper.GroupMapper;
import org.example.lms.model.Course;
import org.example.lms.model.Group;
import org.example.lms.repository.CourseRepository;
import org.example.lms.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        if (groupRepository.existsByGroupName(groupDto.groupName())) {

            throw new AlreadyExistsException("Group name " + groupDto.groupName() + " is already taken");
        }

        Group group = groupMapper.toGroupEntity(groupDto);
        Group newGroup = groupRepository.save(group); // SDJPA executes insert/update (?? is that line necessary)
        return groupMapper.toGroupDto(newGroup);
    }

    @Transactional
    public GroupDto editGroup(Long groupId, GroupDto groupDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("No group with the id: " + groupId + " found"));

        if (groupDto.groupName() != null) {
            group.setGroupName(groupDto.groupName());
        }

        return groupMapper.toGroupDto(group);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        if(!groupRepository.existsById(groupId)) {
            throw new EntityNotFoundException("No group with the id: " + groupId + " found");
        }

        groupRepository.deleteById(groupId);
    }

    @Transactional
    public void assignGroupOnCourse(Long groupId, Long courseId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("No group with the id: " + groupId + " found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("No course with the id: " + courseId + " found"));

        course.getGroups().add(group);
        group.getCourses().add(course);
    }
}
