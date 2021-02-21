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

import guru.springframework.webflux.rest.domain.Vendor;
import guru.springframework.webflux.rest.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class VendorControllerTest {
	
	@Mock private VendorRepository repository;
	private VendorController controller;
	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() throws Exception {
		controller = new VendorController(repository);
		webTestClient = WebTestClient.bindToController(controller).build();
	}

	@Test
	void list() {
		BDDMockito.given(repository.findAll())
			.willReturn(Flux.just(
				Vendor.builder().id("1").name("Vendor 1").build(),
				Vendor.builder().id("2").name("Vendor 2").build()
			));
		webTestClient.get()
			.uri(VendorController.BASE_URL)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Vendor.class).hasSize(2);
	}

	@Test
	void getById() {
		BDDMockito.given(repository.findById(Mockito.anyString()))
		.willReturn(Mono.just(
			Vendor.builder().id("1").name("Vendor 1").build()
		));
	webTestClient.get()
		.uri(VendorController.BASE_URL + "/1")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Vendor.class);
	}

}
