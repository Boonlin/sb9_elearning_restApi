package co.istad.elearningapi.service;

import co.istad.elearningapi.dto.CategoryCreationDto;
import co.istad.elearningapi.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findList();

    CategoryDto findById(Integer id);

    void createNew(CategoryCreationDto categoryCreationDto);

    void deleteById(Integer id);

    void disable(Integer id);

    void editById(Integer id, CategoryCreationDto categoryCreationDto);
}
