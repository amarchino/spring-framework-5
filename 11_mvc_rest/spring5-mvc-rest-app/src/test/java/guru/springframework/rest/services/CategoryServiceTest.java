package guru.springframework.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.model.CategoryDTO;
import guru.springframework.rest.api.v1.mapper.CategoryMapper;
import guru.springframework.rest.domain.Category;
import guru.springframework.rest.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
	private static final Long ID = 2L;
	private static final String NAME = "Jimmy";
	private CategoryService categoryService;
	@Mock private CategoryRepository categoryRepository;

	@BeforeEach
	void setUp() throws Exception {
		categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
	}

	@Test
	void getAllCategories() {
		// Given
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
		when(categoryRepository.findAll()).thenReturn(categories);
		// When
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
		// Then
		assertNotNull(categoryDTOs);
		assertEquals(3, categoryDTOs.size());
	}

	@Test
	void getCategoryByName() {
		// Given
		Category category = Category.builder().id(ID).name(NAME).build();
		when(categoryRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(category));
		// When
		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);
		// Then
		assertNotNull(categoryDTO);
		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}

}
