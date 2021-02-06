package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listAllUoms();
	
}
