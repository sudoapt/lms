package org.example.lms.service;

import lombok.RequiredArgsConstructor;
import org.example.lms.dto.GroupDto;
import org.example.lms.exception.AlreadyExistsException;
import org.example.lms.mapper.GroupMapper;
import org.example.lms.model.Group;
import org.example.lms.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        if (groupRepository.existsByGroupName(groupDto.groupName())) {

            throw new AlreadyExistsException("Group name " + groupDto.groupName() + " is already taken");
        }

        Group group = groupMapper.toGroupEntity(groupDto);
        Group newGroup = groupRepository.save(group); // SDJPA executes insert/update
        return groupMapper.toGroupDto(newGroup);
    }
}
