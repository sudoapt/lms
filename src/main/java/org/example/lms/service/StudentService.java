package org.example.lms.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.StudentDto;
import org.example.lms.dto.StudentFilterDto;
import org.example.lms.mapper.StudentMapper;
import org.example.lms.model.Group;
import org.example.lms.model.Student;
import org.example.lms.repository.GroupRepository;
import org.example.lms.repository.StudentRepository;
import org.example.lms.specification.StudentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;

    @Transactional
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = studentMapper.toStudentEntity(studentDto);
        // check if the passed groups exists in the DB
        Set<Group> groups = new HashSet<>(groupRepository.findAllById(studentDto.groupIDs()));

        if (groups.size() != studentDto.groupIDs().size()) {
            throw new IllegalArgumentException("One or more groups were not found in the DB");
        }

        student.setGroups(groups);

        Student newStudent = studentRepository.save(student);
        return studentMapper.toStudentDto(newStudent);
    }

    @Transactional
    public StudentDto editStudent(Long studentId, StudentDto studentDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("No student with the id: " + studentId + " found" ));

        if (studentDto.firstName() != null) {
            student.setFirstName(studentDto.firstName());
        }

        if (studentDto.lastName() != null) {
            student.setLastName(studentDto.lastName());
        }

        return studentMapper.toStudentDto(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("No student with the id: " + studentId + " found");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void addStudentToGroup(Long studentId, Long groupId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("No student with the id: " + studentId + " found" ));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("No group with the id: " + groupId + " found"));

        // both are done because it's bidirectional @ManyToMany for both entities
        student.getGroups().add(group);
        group.getStudents().add(student);

    }

    @Transactional
    public void removeStudentFromGroup(Long studentId, Long groupId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("No student with the id: " + studentId + " found" ));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("No group with the id: " + groupId + " found"));

        student.getGroups().remove(group);
        group.getStudents().remove(student);
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> filterStudents(StudentFilterDto studentFilterDto, Pageable pageable) {
        Page<Student> studentsPage = studentRepository.findAll (StudentSpecification.studentByFilter(studentFilterDto), pageable);
        return studentsPage.map(studentMapper::toStudentDto);
    }
}
