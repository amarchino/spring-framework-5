package guru.springframework.di.services;

public class I18nGermanGreetingService implements GreetingService {

	private final GreetingRepository greetingRepository;

	public I18nGermanGreetingService(GreetingRepository greetingRepository) {
		this.greetingRepository = greetingRepository;
	}

	@Override
	public String sayGreeting() {
		return greetingRepository.getGermanGreeting();
	}

}
