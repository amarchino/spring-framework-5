package guru.springframework.rest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {
	
	@Autowired private ApplicationContext applicationContext;
	WebTestClient webTestClient;
	
	@BeforeEach
	void setUp() throws Exception {
		webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
	}

	@Test
	void index() {
		webTestClient.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	void formPost() {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("limit", "1");
		
		webTestClient.post()
		.uri("/users")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.body(BodyInserters.fromFormData(formData))
		.exchange()
		.expectStatus().isOk();
	}

}
