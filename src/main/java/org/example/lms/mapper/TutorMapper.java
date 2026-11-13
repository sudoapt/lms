package org.example.lms.mapper;

import org.example.lms.dto.TutorDto;
import org.example.lms.model.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TutorMapper {
    TutorDto toTutorDto(Tutor tutor);

    Tutor tuTutorEntity(TutorDto tutorDto);
}
