package org.example.lms.service;


import lombok.RequiredArgsConstructor;
import org.example.lms.dto.TutorDto;
import org.example.lms.mapper.TutorMapper;
import org.example.lms.model.Tutor;
import org.example.lms.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorService {
    private final TutorRepository tutorRepository;
    private final TutorMapper tutorMapper;

    @Transactional
    public TutorDto createTutor(TutorDto tutorDto) {
        Tutor tutor = tutorMapper.tuTutorEntity(tutorDto);
        Tutor newTutor = tutorRepository.save(tutor);

        return tutorMapper.toTutorDto(newTutor);
    }
}
