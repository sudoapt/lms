package org.example.lms.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.lms.dto.GroupDto;
import org.example.lms.dto.TutorDto;
import org.example.lms.mapper.TutorMapper;
import org.example.lms.model.Course;
import org.example.lms.model.Group;
import org.example.lms.model.Tutor;
import org.example.lms.repository.CourseRepository;
import org.example.lms.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorService {
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final TutorMapper tutorMapper;

    @Transactional
    public TutorDto createTutor(TutorDto tutorDto) {
        Tutor tutor = tutorMapper.tuTutorEntity(tutorDto);
        Tutor newTutor = tutorRepository.save(tutor);

        return tutorMapper.toTutorDto(newTutor);
    }

    // TODO: make schedule editing
    @Transactional
    public TutorDto editTutor(Long tutorId, TutorDto tutorDto) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("No tutor with the id: " + tutorId + " found"));

        if (tutorDto.firstName() != null) {
            tutor.setFirstName(tutorDto.firstName());
        }
        if (tutorDto.lastName() != null) {
            tutor.setLastName(tutorDto.lastName());
        }

        return tutorMapper.toTutorDto(tutor);
    }

    @Transactional
    public void deleteTutor(Long tutorId) {
        if (!tutorRepository.existsById(tutorId)) {
            throw new EntityNotFoundException("No tutor with the id:" + tutorId + " found");
        }
        tutorRepository.deleteById(tutorId);
    }

    @Transactional
    public void assignTutorToCourse(Long tutorId, Long courseId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("No tutor with the id: " + tutorId + " found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("No course with the id: " + courseId + " found"));

        tutor.getCourses().add(course);
        course.setTutor(tutor);
    }
}
