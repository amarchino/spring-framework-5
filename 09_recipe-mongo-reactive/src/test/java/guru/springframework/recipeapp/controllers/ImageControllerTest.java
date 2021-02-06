package guru.springframework.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.service.ImageService;
import guru.springframework.recipeapp.service.RecipeService;
import reactor.core.publisher.Mono;

class ImageControllerTest {
	
	@Mock private ImageService imageService;
	@Mock private RecipeService recipeService;
	private ImageController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		try (AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			controller = new ImageController(recipeService, imageService);
			mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
		}
	}
	
	@Test
	void imageForm() throws Exception {
		// Given
		RecipeCommand command = new RecipeCommand();
		command.setId("1");
		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(command));
		
		// When
		mockMvc.perform(get("/recipe/1/image"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"));
		// Then
		verify(recipeService, times(1)).findCommandById(Mockito.anyString());
	}

	@Test
	void handeImagePost() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring framework Guru".getBytes());
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/recipe/1/show"));
		verify(imageService, times(1)).saveImageFile(Mockito.anyString(), Mockito.any());
	}
	
	@Test
	void renderImageFromDB() throws Exception {
		// Given
		RecipeCommand command = new RecipeCommand();
		command.setId("1");
		String s = "Fake image content";
		Byte[] bytes = new Byte[s.getBytes().length];
		int i = 0;
		for(byte b : s.getBytes()) {
			bytes[i++] = b;
		}
		command.setImage(bytes);
		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(command));
		// When
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
		// Then
			.andExpect(status().isOk())
			.andReturn().getResponse();
		byte[] responseBytes = response.getContentAsByteArray();
		assertEquals(s.getBytes().length, responseBytes.length);
	}

}
