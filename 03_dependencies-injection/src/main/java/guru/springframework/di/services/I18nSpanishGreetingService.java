package guru.springframework.di.services;

public class I18nSpanishGreetingService implements GreetingService {

	private final GreetingRepository greetingRepository;

	public I18nSpanishGreetingService(GreetingRepository greetingRepository) {
		this.greetingRepository = greetingRepository;
	}

	@Override
	public String sayGreeting() {
		return greetingRepository.getSpanishGreeting();
	}

}
