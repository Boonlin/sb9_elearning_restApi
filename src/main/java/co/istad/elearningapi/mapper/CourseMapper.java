package co.istad.elearningapi.mapper;

import co.istad.elearningapi.dto.CourseCreationDto;
import co.istad.elearningapi.dto.CourseDto;
import co.istad.elearningapi.dto.CourseEditionDto;
import co.istad.elearningapi.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toCourseDto(Course course);
    List<CourseDto> toCourseListDto(List<Course> courses);
@Mapping(source = "categoryId", target = "category.id")
@Mapping(source = "instructorId", target = "instructor.id")
Course fromCreationDto(CourseCreationDto courseCreationDto);
void fromEditionDto(@MappingTarget Course course, CourseEditionDto courseEditionDto);

}
