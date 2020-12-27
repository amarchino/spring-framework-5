package guru.springframework.di.controllers;

import guru.springframework.di.services.GreetingService;

public class PropertyInjectedController {

	public GreetingService greetingService;
	
	public String getGreeting() {
		return greetingService.seyGreeting();
	}
}
