package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.CategoryCreationDto;
import co.istad.elearningapi.dto.CategoryDto;
import co.istad.elearningapi.mapper.CategoryMapper;
import co.istad.elearningapi.model.Category;
import co.istad.elearningapi.repository.CategoryRepository;
import co.istad.elearningapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public List<CategoryDto> findList() {
  List<Category> categories = categoryRepository.selectAllCategories();
        return categoryMapper.toListDto(categories);

    }

    @Override
    public CategoryDto findById(Integer id) {
        Category category = categoryRepository.selectCategoryID(id);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public void createNew(CategoryCreationDto categoryCreationDto) {
        Category category = categoryMapper.fromCreationDto(categoryCreationDto);
        categoryRepository.save(category);
    }
    @Override
    public void deleteById(Integer id) {
        if (!categoryRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course has not been found!");
        }
     categoryRepository.deleteById(id);
    }
    @Override
    @Transactional
    public void disable(Integer id) {
    if (!categoryRepository.existsById(id)){
     throw new ResponseStatusException( HttpStatus.NOT_FOUND, "category has not been found!");
    }
    categoryRepository.disableById(id);
    }

    @Override
    public void editById(Integer id, CategoryCreationDto categoryCreationDto) {
    // category = categoryRepository.findById(id).orElseThrow( )
    }
}
