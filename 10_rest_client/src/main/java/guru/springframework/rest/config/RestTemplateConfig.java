package guru.springframework.rest.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		// Need to provide a rest template builder because
		// @RestTemplateAutoConfiguration does not work with webflux
		return new RestTemplateBuilder();
	}
}
