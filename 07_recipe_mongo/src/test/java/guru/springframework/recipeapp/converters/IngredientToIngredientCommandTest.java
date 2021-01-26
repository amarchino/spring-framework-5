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

public class IngredientToIngredientCommandTest {
	private static final Recipe RECIPE = new Recipe();
	private static final BigDecimal QUANTITY = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final String UOM_ID = "2";
	private static final String ID_VALUE = "1";
	private static final String RECIPE_ID = "3";

	private IngredientToIngredientCommand converter;

	@BeforeEach
	public void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		RECIPE.setId(RECIPE_ID);
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
		ingredient.setAmount(QUANTITY);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setUom(null);
		// when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		// then
		assertNull(ingredientCommand.getUom());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		// assertEquals(RECIPE, ingredientCommand.get);
		assertEquals(QUANTITY, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}

	@Test
	public void testConvertWithUom() throws Exception {
		// given
		Ingredient ingredient = Ingredient.builder().id(ID_VALUE).amount(QUANTITY).description(DESCRIPTION).build();
		UnitOfMeasure uom = UnitOfMeasure.builder().id(UOM_ID).build();

		ingredient.setUom(uom);
		// when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		// then
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertNotNull(ingredientCommand.getUom());
		assertEquals(UOM_ID, ingredientCommand.getUom().getId());
		assertEquals(QUANTITY, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}
	
	@Test
	public void testConvertNullRecipe() throws Exception {
		// given
		Ingredient ingredient = new Ingredient();
		// when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		// then
		assertNull(ingredientCommand.getRecipeId());
	}

	@Test
	public void testConvertWithRecipe() throws Exception {
		// given
		Ingredient ingredient = new Ingredient();
		
		// when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		// then
		assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
	}
}
