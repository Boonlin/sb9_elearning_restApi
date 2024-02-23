package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.CategoryCreationDto;
import co.istad.elearningapi.dto.CategoryDto;
import co.istad.elearningapi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    List<CategoryDto> findList() {
        return categoryService.findList();
    }
    @GetMapping("/{id}")
    CategoryDto findById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody CategoryCreationDto categoryCreationDto) {
        categoryService.createNew(categoryCreationDto);
    }
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }
    @PutMapping("/{id}/disable")
    void disable(@PathVariable Integer id) {
        categoryService.disable(id);
    }
    @PutMapping("/{id}")
    void editById(@PathVariable Integer id, @Valid @RequestBody CategoryCreationDto categoryCreationDto) {
        categoryService.editById(id, categoryCreationDto);
    }
}
