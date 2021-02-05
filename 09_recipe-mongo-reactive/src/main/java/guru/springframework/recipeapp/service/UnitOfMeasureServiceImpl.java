package guru.springframework.recipeapp.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	private final UnitOfMeasureRepository uomOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand uomOfMeasureToUnitOfMeasureCommand;

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		return StreamSupport.stream(uomOfMeasureRepository.findAll().spliterator(), false)
				.map(uomOfMeasureToUnitOfMeasureCommand::convert)
				.collect(Collectors.toSet());
	}
	
}
