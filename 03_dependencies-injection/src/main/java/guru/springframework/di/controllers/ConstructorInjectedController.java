package guru.springframework.di.controllers;

import org.springframework.stereotype.Controller;

import guru.springframework.di.services.GreetingService;

@Controller
public class ConstructorInjectedController {

	private final GreetingService greetingService;

	public ConstructorInjectedController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String getGreeting() {
		return greetingService.seyGreeting();
	}
}
