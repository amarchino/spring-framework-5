package guru.springframework.joke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;

@Configuration
public class ChuckConfiguration {

	@Bean
	public ChuckNorrisQuotes chuckNorrisQuote() {
		return new ChuckNorrisQuotes();
	}
}
