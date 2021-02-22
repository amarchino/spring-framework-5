package guru.springframework.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
		.info(
			new Info()
			.title("Spring Framework 5: Beginner to Guru")
			.description("Spring REST API lessons")
			.version("1.0.0")
			.contact(
				new Contact()
				.name("Alessandro Marchino")
				.email("marchino.alessandro@gmail.com")
				.url("https://github.com/amarchino")
			)
			.license(
				new License()
				.name("MIT")));
	}
}
