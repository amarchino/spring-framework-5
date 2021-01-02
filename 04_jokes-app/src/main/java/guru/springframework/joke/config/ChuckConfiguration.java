package guru.springframework.joke.config;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;

//@Configuration
public class ChuckConfiguration {

//	@Bean
	public ChuckNorrisQuotes chuckNorrisQuote() {
		return new ChuckNorrisQuotes();
	}
}
