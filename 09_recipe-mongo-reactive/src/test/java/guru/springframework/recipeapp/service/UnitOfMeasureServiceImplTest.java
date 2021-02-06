package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Flux;

class UnitOfMeasureServiceImplTest {
	private final UnitOfMeasureToUnitOfMeasureCommand uomOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	private UnitOfMeasureServiceImpl uomOfMeasureServiceImpl;
	@Mock private UnitOfMeasureReactiveRepository uomOfMeasureReactiveRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		try(AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
			uomOfMeasureServiceImpl = new UnitOfMeasureServiceImpl(uomOfMeasureReactiveRepository, uomOfMeasureToUnitOfMeasureCommand);
		}
	}

	@Test
	void listAllUoms() {
		// Given
		when(uomOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(
			UnitOfMeasure.builder().id("1").build(),
			UnitOfMeasure.builder().id("2").build()
		));
		
		List<UnitOfMeasureCommand> commands = uomOfMeasureServiceImpl.listAllUoms().collectList().block();
		assertEquals(2, commands.size());
		verify(uomOfMeasureReactiveRepository, times(1)).findAll();
	}

}
