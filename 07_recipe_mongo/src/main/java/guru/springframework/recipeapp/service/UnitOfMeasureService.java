package guru.springframework.recipeapp.service;

import java.util.Set;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
	
}
