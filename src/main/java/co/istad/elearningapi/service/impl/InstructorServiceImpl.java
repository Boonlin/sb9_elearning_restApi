package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.InstructorCreationDto;
import co.istad.elearningapi.dto.InstructorDto;
import co.istad.elearningapi.dto.InstructorEditionDto;
import co.istad.elearningapi.mapper.InstructorMapper;
import co.istad.elearningapi.model.Instructor;
import co.istad.elearningapi.repository.CategoryRepository;
import co.istad.elearningapi.repository.InstructorRepository;
import co.istad.elearningapi.repository.TestRepository;
import co.istad.elearningapi.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final CategoryRepository categoryRepository;

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public List<InstructorDto> search(String familyName, String givenName, String biography) {
        List<Instructor> instructors = new ArrayList<>();
        if (!familyName.isBlank()) {
            List<Instructor> instructorByFamilyName = instructorRepository.findByFamilyNameIgnoreCase(familyName);
            instructors.addAll(instructorByFamilyName);
        }

        if (!givenName.isBlank()) {
            List<Instructor> instructorByGivenName = instructorRepository.findByGivenNameIgnoreCase(givenName);
            instructors.addAll(instructorByGivenName);
        }

        if (!biography.isBlank()) {
            List<Instructor> instructorByBiography = instructorRepository.findByBiographyContainsIgnoreCase(biography);
            instructors.addAll(instructorByBiography);
        }

        instructors = instructors.stream().filter(distinctByKey(Instructor::getId)).toList();

        return instructorMapper.toInstructorListDto(instructors);
    }

    @Override
    public InstructorDto findByIdAndNationalIdCard(Integer id, String nationalIdCard) {
        Instructor instructor = instructorRepository.findByIdAndNationalIdCard(id, nationalIdCard).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor has not been found"));
        return instructorMapper.toInstructorDto(instructor);
    }

    @Override
    public InstructorDto findById(Integer id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor has not been found"));
        return instructorMapper.toInstructorDto(instructor);
    }

    @Override
    public void deleteById(Integer id) {
        if (!instructorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor has not been found");
        }
        instructorRepository.deleteById(id);
    }

    @Override // Update partially
    public InstructorDto editById(Integer id, InstructorEditionDto instructorEditionDto) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor has not been found"));
        instructorMapper.fromInstructorEditionDto(instructor, instructorEditionDto);
        instructorRepository.save(instructor);

        return this.findById(id);
    }

    @Override
    public void createNew(InstructorCreationDto instructorCreationDto) {
        Instructor instructor = instructorMapper.fromInstructorCreationDto(instructorCreationDto);
        // Save into db
        instructorRepository.save(instructor);
    }

    @Transactional
    @Override
    public List<InstructorDto> findList(String q) {

        List<Integer> idForRemove = List.of(6, 100);

        for (Integer id : idForRemove) {
            categoryRepository.deleteById(id);
            if (id == 100) {
                throw new RuntimeException("Id doesn't exist!");
            }
        }

        // Find from db
        List<Instructor> instructors = instructorRepository.findAll();
        return instructorMapper.toInstructorListDto(instructors);
    }

}
