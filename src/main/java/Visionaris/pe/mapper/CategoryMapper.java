package Visionaris.pe.mapper;

import Visionaris.pe.dto.category.CategoryDTO;
import Visionaris.pe.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category categoria);
    Category toEntity(CategoryDTO categoriaDTO);
    List<CategoryDTO> toDtoList(List<Category> categorias);
}
