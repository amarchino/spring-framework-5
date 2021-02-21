package guru.springframework.webflux.rest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.webflux.rest.domain.Category;
import guru.springframework.webflux.rest.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	
	@Mock private CategoryRepository categoryRepository;
	private CategoryController categoryController;
	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() throws Exception {
		categoryController = new CategoryController(categoryRepository);
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}

	@Test
	void list() {
		BDDMockito.given(categoryRepository.findAll())
			.willReturn(Flux.just(
				Category.builder().id("1").description("Category 1").build(),
				Category.builder().id("2").description("Category 2").build()
			));
		webTestClient.get()
			.uri(CategoryController.BASE_URL)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Category.class).hasSize(2);
	}

	@Test
	void getById() {
		BDDMockito.given(categoryRepository.findById(Mockito.anyString()))
		.willReturn(Mono.just(
			Category.builder().id("1").description("Category 1").build()
		));
	webTestClient.get()
		.uri(CategoryController.BASE_URL + "/1")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Category.class);
	}

}
