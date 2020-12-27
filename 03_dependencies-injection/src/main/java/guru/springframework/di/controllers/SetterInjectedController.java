package guru.springframework.di.controllers;

import guru.springframework.di.services.GreetingService;

public class SetterInjectedController {

	private GreetingService greetingService;
	
	public void setGreetingService(GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	public String getGreeting() {
		return greetingService.seyGreeting();
	}
}
