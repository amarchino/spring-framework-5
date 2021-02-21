package guru.springframework.rest.controllers.v1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.rest.api.v1.model.CategoryDTO;
import guru.springframework.rest.services.CategoryService;
import guru.springframework.rest.services.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
	
	private static final String NAME = "Jim";
	@Mock private CategoryService categoryService;
	@InjectMocks private CategoryController categoryController;
	private	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.standaloneSetup(categoryController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	@Test
	public void getAllCategories() throws Exception {
		List<CategoryDTO> categories = Arrays.asList(
			CategoryDTO.builder().id(1L).name(NAME).build(),
			CategoryDTO.builder().id(2L).name("Bob").build()
		);
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get(CategoryController.BASE_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.categories", Matchers.hasSize(2)));
	}
	@Test
	public void getCategoryByName() throws Exception {
		CategoryDTO categoryDTO = CategoryDTO.builder().id(1L).name(NAME).build();
		when(categoryService.getCategoryByName(Mockito.anyString())).thenReturn(categoryDTO);
		
		mockMvc.perform(get(CategoryController.BASE_URL + "/" + NAME).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", Matchers.equalTo(NAME)));
	}
	
	@Test
	public void getCategoryByNameNotFound() throws Exception {
		when(categoryService.getCategoryByName(Mockito.anyString())).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get(CategoryController.BASE_URL + "/" + NAME))
			.andExpect(status().isNotFound());
	}
}
