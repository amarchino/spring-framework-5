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
	@Profile({"default", "EN"})
	GreetingService i18nEnglishGreetingService(GreetingServiceFactory greetingServiceFactory) {
		return greetingServiceFactory.createGreetingService("EN");
	}
	@Bean
	@Primary
	@Profile("ES")
	GreetingService i18nSpanishGreetingService(GreetingServiceFactory greetingServiceFactory) {
		return greetingServiceFactory.createGreetingService("ES");
	}
	@Bean
	@Primary
	@Profile("DE")
	GreetingService i18nGermanGreetingService(GreetingServiceFactory greetingServiceFactory) {
		return greetingServiceFactory.createGreetingService("DE");
	}
}
