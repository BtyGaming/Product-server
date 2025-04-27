package Visionaris.pe.service.Impl;

import Visionaris.pe.dto.category.CategoryDTO;
import Visionaris.pe.entity.Category;
import Visionaris.pe.mapper.CategoryMapper;
import Visionaris.pe.repository.CategoryRepository;
import Visionaris.pe.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl  implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return mapper.toDto(category);
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        Category category = mapper.toEntity(dto);
        return mapper.toDto(repository.save(category));
    }


    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        existingCategory.setName(dto.getName());
        existingCategory.setDescription(dto.getDescription());
        return mapper.toDto(repository.save(existingCategory));
    }
    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

}
