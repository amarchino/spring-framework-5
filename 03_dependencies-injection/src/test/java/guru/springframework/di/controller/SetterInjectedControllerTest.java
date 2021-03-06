package guru.springframework.di.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.di.controllers.SetterInjectedController;
import guru.springframework.di.services.ConstructorGreetingService;

public class SetterInjectedControllerTest {
	
	SetterInjectedController controller;
	
	@BeforeEach
	void setUp() {
		controller = new SetterInjectedController();
		controller.setGreetingService(new ConstructorGreetingService());
	}
	
	@Test
	void getGreeting() {
		System.out.println(controller.getGreeting());
	}

}
