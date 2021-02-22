package guru.springframework.rest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import guru.springframework.rest.api.v1.model.CategoryDTO;
import guru.springframework.rest.domain.Category;

class CategoryMapperTest {
	
	private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	void categoryToCategoryDTO() {
		// Given
		Category category = Category.builder().name("Joe").id(1L).build();
		// When
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
		// Then
		assertEquals(category.getId(), categoryDTO.getId());
		assertEquals(category.getName(), categoryDTO.getName());
	}

	@Test
	void categoryDTOToCategory() {
		// Given
		CategoryDTO categoryDTO = CategoryDTO.builder().name("Joe").id(1L).build();
		// When
		Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
		// Then
		assertEquals(categoryDTO.getId(), category.getId());
		assertEquals(categoryDTO.getName(), category.getName());
	}

}
