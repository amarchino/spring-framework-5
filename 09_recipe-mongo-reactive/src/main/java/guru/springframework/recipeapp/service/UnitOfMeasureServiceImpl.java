package guru.springframework.recipeapp.service;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	private final UnitOfMeasureReactiveRepository uomOfMeasureReactiveRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand uomOfMeasureToUnitOfMeasureCommand;

	@Override
	public Flux<UnitOfMeasureCommand> listAllUoms() {
		return uomOfMeasureReactiveRepository.findAll()
				.map(uomOfMeasureToUnitOfMeasureCommand::convert);
	}
	
}
