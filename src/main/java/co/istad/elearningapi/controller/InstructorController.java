package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.InstructorCreationDto;
import co.istad.elearningapi.dto.InstructorDto;
import co.istad.elearningapi.dto.InstructorEditionDto;
import co.istad.elearningapi.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;
    @GetMapping("/search")
    List<InstructorDto> search(@RequestParam(required = false, defaultValue = "") String familyName,
                                   @RequestParam(required = false, defaultValue = "") String givenName,
                                   @RequestParam(required = false, defaultValue = "") String biography) {
        return instructorService.search(familyName, givenName, biography);
    }
    @PatchMapping("/{id}")
    InstructorDto editById(@PathVariable Integer id,
                           @RequestBody InstructorEditionDto instructorEditionDto) {
        return instructorService.editById(id, instructorEditionDto);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        instructorService.deleteById(id);
    }
    @GetMapping("/{id}")
    InstructorDto findById(@PathVariable Integer id) {
        return instructorService.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody InstructorCreationDto instructorCreationDto) {
        System.out.println("REQUEST BODY: " + instructorCreationDto);
        instructorService.createNew(instructorCreationDto);
    }
    @GetMapping("/{id}/{nationalIdCard}")
    InstructorDto findById(@PathVariable Integer id,
                           @PathVariable String nationalIdCard) {
        System.out.println("Path variable ID: " + id);
        return instructorService.findByIdAndNationalIdCard(id, nationalIdCard);
    }
    @GetMapping
    List<InstructorDto> findList(@RequestParam(required = false, defaultValue = "") String q) {
        System.out.println("REQUEST PARAM: " + q);
        return instructorService.findList(q);
    }
}
