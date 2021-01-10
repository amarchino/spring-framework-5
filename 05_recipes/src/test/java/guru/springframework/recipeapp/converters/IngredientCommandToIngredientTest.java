package guru.springframework.recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.domain.UnitOfMeasure;

public class IngredientCommandToIngredientTest {
	private static final Recipe RECIPE = new Recipe();
	private static final BigDecimal AMOUNT = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final Long UOM_ID = 2L;
	private static final Long ID_VALUE = 1L;

	private IngredientToIngredientCommand converter;

	@BeforeEach
	public void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
	public void testNullConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Ingredient()));
	}

	@Test
	public void testConvertNullUOM() throws Exception {
		// given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setRecipe(RECIPE);
		ingredient.setQuantity(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setUnit(null);
		// when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		// then
		assertNull(ingredientCommand.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		// assertEquals(RECIPE, ingredientCommand.get);
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}

	@Test
	public void testConvertWithUom() throws Exception {
		// given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setRecipe(RECIPE);
		ingredient.setQuantity(AMOUNT);
		ingredient.setDescription(DESCRIPTION);

		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(UOM_ID);

		ingredient.setUnit(uom);
		// when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		// then
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertNotNull(ingredientCommand.getUnitOfMeasure());
		assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
		// assertEquals(RECIPE, ingredientCommand.get);
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}
}
