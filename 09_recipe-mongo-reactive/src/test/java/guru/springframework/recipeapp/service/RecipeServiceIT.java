package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.recipeapp.bootstrap.RecipeBootstrap;
import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.converters.CategoryCommandToCategory;
import guru.springframework.recipeapp.converters.CategoryToCategoryCommand;
import guru.springframework.recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.converters.NotesCommandToNotes;
import guru.springframework.recipeapp.converters.NotesToNotesCommand;
import guru.springframework.recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.CategoryRepository;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeServiceIT {

	public static final String NEW_DESCRIPTION = "New Description";

    @Autowired private RecipeRepository recipeRepository;
    @Autowired private UnitOfMeasureRepository uomOfMeasureRepository;
	@Autowired private CategoryRepository categoryRepository;
	
	private CategoryCommandToCategory categoryConverter = new CategoryCommandToCategory();
	private NotesCommandToNotes noteConverter = new NotesCommandToNotes();
	private UnitOfMeasureCommandToUnitOfMeasure uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
	private IngredientCommandToIngredient ingredientConverter = new IngredientCommandToIngredient(uomConverter);
	private RecipeCommandToRecipe recipeCommandToRecipe = new RecipeCommandToRecipe(ingredientConverter, noteConverter, categoryConverter);

	private CategoryToCategoryCommand category2Converter = new CategoryToCategoryCommand();
	private NotesToNotesCommand note2Converter = new NotesToNotesCommand();
	private UnitOfMeasureToUnitOfMeasureCommand uom2Converter = new UnitOfMeasureToUnitOfMeasureCommand();
	private IngredientToIngredientCommand ingredient2Converter = new IngredientToIngredientCommand(uom2Converter);
	private RecipeToRecipeCommand recipeToRecipeCommand = new RecipeToRecipeCommand(ingredient2Converter, note2Converter, category2Converter);
	
	private RecipeService recipeService;
	
	@BeforeEach
	public void setUp() {
		RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, uomOfMeasureRepository, recipeRepository);
		recipeBootstrap.onApplicationEvent(null);
		
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

    @Test
    public void testSaveOfDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
