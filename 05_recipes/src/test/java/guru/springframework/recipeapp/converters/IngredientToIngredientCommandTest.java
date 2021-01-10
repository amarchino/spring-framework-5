package guru.springframework.recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.Ingredient;

public class IngredientToIngredientCommandTest {
	private static final BigDecimal QUANTITY = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final Long ID_VALUE = 1L;
	private static final Long UOM_ID = 2L;

	private IngredientCommandToIngredient converter;

	@BeforeEach
	public void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	public void convert() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setQuantity(QUANTITY);
		command.setDescription(DESCRIPTION);
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(UOM_ID);
		command.setUnit(unitOfMeasureCommand);

		// when
		Ingredient ingredient = converter.convert(command);

		// then
		assertNotNull(ingredient);
		assertNotNull(ingredient.getUnit());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(QUANTITY, ingredient.getQuantity());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(UOM_ID, ingredient.getUnit().getId());
	}

	@Test
	public void convertWithNullUOM() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setQuantity(QUANTITY);
		command.setDescription(DESCRIPTION);

		// when
		Ingredient ingredient = converter.convert(command);

		// then
		assertNotNull(ingredient);
		assertNull(ingredient.getUnit());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(QUANTITY, ingredient.getQuantity());
		assertEquals(DESCRIPTION, ingredient.getDescription());

	}
}
