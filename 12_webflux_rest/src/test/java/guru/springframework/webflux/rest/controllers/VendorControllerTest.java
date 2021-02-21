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
	
	@Test
	void create() {
		BDDMockito.given(repository.saveAll(Mockito.any(Publisher.class)))
		.willReturn(Flux.just(
			Vendor.builder().id("1").name("Vendor 1").build()
		));
		Mono<Vendor> vendor = Mono.just(Vendor.builder().name("Some vendor").build());
		webTestClient.post()
			.uri(VendorController.BASE_URL)
			.body(vendor, Vendor.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isCreated();
	}

	@Test
	void update() {
		BDDMockito.given(repository.save(Mockito.any(Vendor.class)))
		.willReturn(Mono.just(
			Vendor.builder().id("1").name("Vendor 1").build()
		));
		Mono<Vendor> vendor = Mono.just(Vendor.builder().name("Some vendor").build());
		webTestClient.put()
			.uri(VendorController.BASE_URL + "/1")
			.body(vendor, Vendor.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	void patch() {
		BDDMockito.given(repository.findById(Mockito.anyString()))
		.willReturn(Mono.just(
			Vendor.builder().id("1").name("Vendor 1").build()
		));
		
		BDDMockito.given(repository.save(Mockito.any(Vendor.class)))
		.willReturn(Mono.just(
			Vendor.builder().id("1").name("Some vendor").build()
		));
		Mono<Vendor> vendor = Mono.just(Vendor.builder().name("Some vendor").build());
		webTestClient.patch()
			.uri(VendorController.BASE_URL + "/1")
			.body(vendor, Vendor.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
		verify(repository, times(1)).save(Mockito.any(Vendor.class));
	}
	
	@Test
	void patchNoop() {
		BDDMockito.given(repository.findById(Mockito.anyString()))
		.willReturn(Mono.just(
			Vendor.builder().id("1").name("Vendor 1").build()
		));
		
		Mono<Vendor> vendor = Mono.just(Vendor.builder().name("Vendor 1").build());
		webTestClient.patch()
			.uri(VendorController.BASE_URL + "/1")
			.body(vendor, Vendor.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
		verify(repository, never()).save(Mockito.any(Vendor.class));
	}
}
