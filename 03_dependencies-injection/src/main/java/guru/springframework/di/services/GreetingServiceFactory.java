package guru.springframework.di.services;

public class GreetingServiceFactory {
	
	private final GreetingRepository greetingRepository;
	
	public GreetingServiceFactory(GreetingRepository greetingRepository) {
		this.greetingRepository = greetingRepository;
	}

	public GreetingService createGreetingService(String lang) {
		switch(lang) {
			case "EN": return new I18nEnglishGreetingService(greetingRepository);
			case "ES": return new I18nSpanishGreetingService(greetingRepository);
			case "DE": return new I18nGermanGreetingService(greetingRepository);
			default: return new I18nEnglishGreetingService(greetingRepository);
		}
	}

}
