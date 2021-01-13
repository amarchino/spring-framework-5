package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;

class UnitOfMeasureServiceImplTest {
	private final UnitOfMeasureToUnitOfMeasureCommand uomOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	private UnitOfMeasureServiceImpl uomOfMeasureServiceImpl;
	@Mock private UnitOfMeasureRepository uomOfMeasureRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		try(AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
			uomOfMeasureServiceImpl = new UnitOfMeasureServiceImpl(uomOfMeasureRepository, uomOfMeasureToUnitOfMeasureCommand);
		}
	}

	@Test
	void listAllUoms() {
		// Given
		Set<UnitOfMeasure> uomOfMeasures = Set.of(
			UnitOfMeasure.builder().id(1L).build(),
			UnitOfMeasure.builder().id(2L).build()
		);
		
		when(uomOfMeasureRepository.findAll()).thenReturn(uomOfMeasures);
		
		Set<UnitOfMeasureCommand> commands = uomOfMeasureServiceImpl.listAllUoms();
		assertEquals(2, commands.size());
		verify(uomOfMeasureRepository, times(1)).findAll();
	}

}
