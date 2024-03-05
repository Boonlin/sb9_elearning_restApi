package co.istad.elearningapi.mapper;

import co.istad.elearningapi.dto.InstructorCreationDto;
import co.istad.elearningapi.dto.InstructorDto;
import co.istad.elearningapi.dto.InstructorEditionDto;
import co.istad.elearningapi.model.Instructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    // 1. Source Type
    // 2. Target Type
    List<InstructorDto> toInstructorListDto(List<Instructor> instructors);

    InstructorDto toInstructorDto(Instructor instructor);
    Instructor fromInstructorCreationDto(InstructorCreationDto instructorCreationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromInstructorEditionDto(@MappingTarget Instructor instructor, InstructorEditionDto instructorEditionDto);
}
