package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.CourseCreationDto;
import co.istad.elearningapi.dto.CourseDto;
import co.istad.elearningapi.dto.CourseEditionDto;
import co.istad.elearningapi.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @GetMapping
    List<CourseDto> findList() {
        return courseService.findList();
    }
    @GetMapping("{id}")
    CourseDto findById(@PathVariable Long id) {
        System.out.println(" Path variable ID : " + id);
        return courseService.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody CourseCreationDto courseCreationDto) {
        System.out.println("REQUEST BODY:" + courseCreationDto);
        courseService.createNew(courseCreationDto);
    }
    @PutMapping("/{id}")
    void editById(@PathVariable Long id, @Valid @RequestBody CourseEditionDto courseEditionDto) {
        courseService.editById(id, courseEditionDto);
    }
    @PutMapping("/{id}/disable")
    void disableById(@PathVariable Long id) {
        courseService.disableById(id);
    }
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        courseService.deleteById(id);
    }
}
