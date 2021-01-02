package guru.springframework.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import guru.springframework.di.services.GreetingRepository;
import guru.springframework.di.services.GreetingService;
import guru.springframework.di.services.GreetingServiceFactory;

@Configuration
public class GreetingServiceConfig {
	
	@Bean
	GreetingServiceFactory GreetingServiceFactory(GreetingRepository greetingRepository) {
		return new GreetingServiceFactory(greetingRepository);
	}

	@Bean
	@Primary
	@Profile({"default", "en"})
	GreetingService i18nEnglishGreetingService(GreetingServiceFactory greetingServiceFactory) {
		return greetingServiceFactory.createGreetingService("en");
	}
	@Bean
	@Primary
	@Profile("es")
	GreetingService i18nSpanishGreetingService(GreetingServiceFactory greetingServiceFactory) {
		return greetingServiceFactory.createGreetingService("es");
	}
	@Bean
	@Primary
	@Profile("de")
	GreetingService i18nGermanGreetingService(GreetingServiceFactory greetingServiceFactory) {
		return greetingServiceFactory.createGreetingService("de");
	}
}
