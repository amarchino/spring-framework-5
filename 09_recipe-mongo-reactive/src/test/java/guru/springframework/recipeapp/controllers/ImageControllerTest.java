package guru.springframework.recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.service.ImageService;
import guru.springframework.recipeapp.service.RecipeService;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ImageController.class)
@AutoConfigureWebTestClient(timeout = "36000")
class ImageControllerTest {
	
	@MockBean private RecipeService recipeService;
	@MockBean private ImageService imageService;
	@Autowired private WebTestClient webTestClient;
	
	@Test
	void imageForm() throws Exception {
		// Given
		RecipeCommand command = new RecipeCommand();
		command.setId("1");
		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(command));
		
		// When
		webTestClient
		.get()
		.uri("/recipe/1/image")
		.exchange()
		.expectStatus().isOk();
		
		// Then
		verify(recipeService, times(1)).findCommandById(Mockito.anyString());
	}

	@Test
	@Disabled
	void handeImagePost() throws Exception {
//		when(imageService.saveImageFile(Mockito.anyString(), Mockito.any())).thenReturn(Mono.empty());
//		
//		MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring framework Guru".getBytes());
//		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(header().string("Location", "/recipe/1/show"));
//		verify(imageService, times(1)).saveImageFile(Mockito.anyString(), Mockito.any());
	}
	
	@Test
	@Disabled
	void renderImageFromDB() throws Exception {
		// Given
//		RecipeCommand command = new RecipeCommand();
//		command.setId("1");
//		String s = "Fake image content";
//		Byte[] bytes = new Byte[s.getBytes().length];
//		int i = 0;
//		for(byte b : s.getBytes()) {
//			bytes[i++] = b;
//		}
//		command.setImage(bytes);
//		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(command));
//		// When
//		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
//		// Then
//			.andExpect(status().isOk())
//			.andReturn().getResponse();
//		byte[] responseBytes = response.getContentAsByteArray();
//		assertEquals(s.getBytes().length, responseBytes.length);
	}

}
