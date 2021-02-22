package guru.springframework.rest.services;

import java.util.List;

import guru.springframework.model.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryByName(String name);
}
