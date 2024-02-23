package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.CourseCreationDto;
import co.istad.elearningapi.dto.CourseDto;
import co.istad.elearningapi.dto.CourseEditionDto;
import co.istad.elearningapi.mapper.CourseMapper;
import co.istad.elearningapi.model.Course;
import co.istad.elearningapi.repository.CourseRepository;
import co.istad.elearningapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.management.RuntimeErrorException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> findList() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toCourseListDto(courses);
    }

    @Override
    public CourseDto findById(Long id) {
        Course course= courseRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Course has not been found"))   ;
    return courseMapper.toCourseDto(course);
    }

    @Override
    public void createNew(CourseCreationDto courseCreationDto) {
       // System.out.println("Before Map:" + courseCreationDto);
        Course course= courseMapper.fromCreationDto(courseCreationDto);
//        System.out.println("After Map:" + course.getInstructor());
//        System.out.println("After Map:" + course.getCategory());
//        System.out.println("After Map:" + course.getInstructor().getId());
//        System.out.println("After Map:" + course.getCategory().getId());
       // course.setIsDeleted(false);
        courseRepository.save(course);
    }

    @Override
    public void editById(Long id, CourseEditionDto courseEditionDto) {
        Course course = courseRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Course has not been found!"));
        System.out.println("Before map :" + course.getTitle());
      courseMapper.fromEditionDto(course, courseEditionDto);
        System.out.println("After map:" + course.getTitle());
        courseRepository.save(course);
    }
@Transactional
    @Override
    public void disableById(Long id) {
        if (!courseRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course has not been found!");
        }
        courseRepository.updateIsDeletedById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course has not been found!");
        }
        courseRepository.deleteById(id);
    }
}
