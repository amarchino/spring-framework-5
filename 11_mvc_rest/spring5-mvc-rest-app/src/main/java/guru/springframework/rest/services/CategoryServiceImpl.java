package guru.springframework.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import guru.springframework.model.CategoryDTO;
import guru.springframework.rest.api.v1.mapper.CategoryMapper;
import guru.springframework.rest.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository
			.findAll()
			.stream()
			.map(categoryMapper::categoryToCategoryDTO)
			.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return categoryRepository.findByName(name)
				.map(categoryMapper::categoryToCategoryDTO)
				.orElseThrow(ResourceNotFoundException::new);
	}

}
