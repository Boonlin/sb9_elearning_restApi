package co.istad.elearningapi.mapper;

import co.istad.elearningapi.dto.CategoryCreationDto;
import co.istad.elearningapi.dto.CategoryDto;
import co.istad.elearningapi.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
List< CategoryDto> toListDto(List<Category> categories);
CategoryDto toCategoryDto( Category category);

Category fromCreationDto(CategoryCreationDto categoryCreationDto);
}
