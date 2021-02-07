package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
	
	@Mock private RecipeReactiveRepository recipeReactiveRepository;
	private ImageService imageService;

	@BeforeEach
	void setUp() throws Exception {
		imageService = new ImageServiceImpl(recipeReactiveRepository);
	}

	@Test
	void saveImageFile() throws IOException {
		// Given
		String id = "1";
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
		
		Recipe recipe = Recipe.builder().id(id).build();
		when(recipeReactiveRepository.findById(Mockito.anyString())).thenReturn(Mono.just(recipe));
		when(recipeReactiveRepository.save(Mockito.any(Recipe.class))).thenReturn(Mono.just(recipe));
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		// When
		imageService.saveImageFile(id, multipartFile).block();
		
		// Then
		verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
