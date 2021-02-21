package guru.springframework.webflux.rest.controllers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.webflux.rest.domain.Category;
import guru.springframework.webflux.rest.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	
	@Mock private CategoryRepository repository;
	private CategoryController controller;
	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() throws Exception {
		controller = new CategoryController(repository);
		webTestClient = WebTestClient.bindToController(controller).build();
	}

	@Test
	void list() {
		BDDMockito.given(repository.findAll())
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
		BDDMockito.given(repository.findById(Mockito.anyString()))
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
	
	@Test
	void create() {
		BDDMockito.given(repository.saveAll(Mockito.any(Publisher.class)))
		.willReturn(Flux.just(
			Category.builder().id("1").description("Category 1").build()
		));
		Mono<Category> category = Mono.just(Category.builder().description("Some category").build());
		webTestClient.post()
			.uri(CategoryController.BASE_URL)
			.body(category, Category.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isCreated();
	}

	@Test
	void update() {
		BDDMockito.given(repository.save(Mockito.any(Category.class)))
		.willReturn(Mono.just(
			Category.builder().id("1").description("Category 1").build()
		));
		Mono<Category> category = Mono.just(Category.builder().description("Some category").build());
		webTestClient.put()
			.uri(CategoryController.BASE_URL + "/1")
			.body(category, Category.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
	}
	
	@Test
	void patch() {
		BDDMockito.given(repository.findById(Mockito.anyString()))
		.willReturn(Mono.just(
			Category.builder().id("1").description("Category 1").build()
		));
		
		BDDMockito.given(repository.save(Mockito.any(Category.class)))
		.willReturn(Mono.just(
			Category.builder().id("1").description("Some category").build()
		));
		Mono<Category> category = Mono.just(Category.builder().description("Some category").build());
		webTestClient.patch()
			.uri(CategoryController.BASE_URL + "/1")
			.body(category, Category.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
		verify(repository, times(1)).save(Mockito.any(Category.class));
	}
	
	@Test
	void patchNoop() {
		BDDMockito.given(repository.findById(Mockito.anyString()))
		.willReturn(Mono.just(
			Category.builder().id("1").description("Category 1").build()
		));
		
		Mono<Category> category = Mono.just(Category.builder().description("Category 1").build());
		webTestClient.patch()
			.uri(CategoryController.BASE_URL + "/1")
			.body(category, Category.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
		verify(repository, never()).save(Mockito.any(Category.class));
	}
}
