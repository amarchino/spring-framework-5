package guru.springframework.recipeapp.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.recipeapp.domain.Category;
import guru.springframework.recipeapp.domain.Difficulty;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Notes;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.CategoryRepository;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import guru.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
	
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final RecipeRepository recipeRepository;
	
	@Autowired
	private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	public RecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository uomOfMeasureRepository, RecipeRepository recipeRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = uomOfMeasureRepository;
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		categoryRepository.saveAll(getCategories());
		unitOfMeasureRepository.saveAll(getUnitsOfMeasure());
		recipeRepository.saveAll(getRecipes());
		log.debug("Loaded bootstrap data");

		log.error("#########");
		log.error("Count: " + unitOfMeasureReactiveRepository.count().block().toString());
		log.error("#########");

	}
	
	private List<UnitOfMeasure> getUnitsOfMeasure() {
		return Arrays.asList(
			UnitOfMeasure.builder().description("Teaspoon").build(),
			UnitOfMeasure.builder().description("Tablespoon").build(),
			UnitOfMeasure.builder().description("Cup").build(),
			UnitOfMeasure.builder().description("Pinch").build(),
			UnitOfMeasure.builder().description("Ounce").build(),
			UnitOfMeasure.builder().description("Each").build(),
			UnitOfMeasure.builder().description("Dash").build(),
			UnitOfMeasure.builder().description("Pint").build()
		);
	}
	
	private List<Category> getCategories() {
		return Arrays.asList(
			Category.builder().description("American").build(),
			Category.builder().description("Italian").build(),
			Category.builder().description("Mexican").build(),
			Category.builder().description("Fast Food").build()
		);
	}
	
	private List<Recipe> getRecipes() {
		List<Recipe> recipes = new ArrayList<>(2);
		// Unit of Measures
		UnitOfMeasure eachUom = getUnitOfMeasure("Each");
		UnitOfMeasure tablespoonUom = getUnitOfMeasure("Tablespoon");
		UnitOfMeasure teaspoonUom = getUnitOfMeasure("Teaspoon");
		UnitOfMeasure dashUom = getUnitOfMeasure("Dash");
		UnitOfMeasure pintUom = getUnitOfMeasure("Pint");
		UnitOfMeasure cupUom = getUnitOfMeasure("Cup");
		// Categories
		Category american = getCategory("American");
		Category mexican = getCategory("Mexican");
		
		// Guacamole
		Recipe guacamole = new Recipe();
		guacamole.setDescription("Perfect Guacamole");
		guacamole.setPrepTime(10);
		guacamole.setCookTime(0);
		guacamole.setDifficulty(Difficulty.EASY);
		guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		guacamole.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
				+ "\n"
				+ "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n"
				+ "\n"
				+ "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
				+ "\n"
				+ "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
				+ "\n"
				+ "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
				+ "\n"
				+ "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n"
				+ "\n"
				+ "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");
		
		Notes guacamoleNote = new Notes();
		guacamoleNote.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n"
				+ "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). You can get creative with homemade guacamole!\n"
				+ "The simplest version of guacamole is just mashed avocados with salt. Don’t let the lack of availability of other ingredients stop you from making guacamole.\n"
				+ "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.");
		guacamole.setNotes(guacamoleNote);
		
		guacamole.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2), eachUom));
		guacamole.addIngredient(new Ingredient("Salt", new BigDecimal("0.25"), teaspoonUom));
		guacamole.addIngredient(new Ingredient("Fresh lime", new BigDecimal(1), tablespoonUom));
		guacamole.addIngredient(new Ingredient("Minced red onion or thinly sliced green onion", new BigDecimal(2), teaspoonUom));
		guacamole.addIngredient(new Ingredient("Serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
		guacamole.addIngredient(new Ingredient("Cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), teaspoonUom));
		guacamole.addIngredient(new Ingredient("Freshly grated black pepper", new BigDecimal(1), dashUom));
		guacamole.addIngredient(new Ingredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal("0.5"), eachUom));
		guacamole.addIngredient(new Ingredient("Cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), teaspoonUom));
		
		guacamole.getCategories().add(american);
		guacamole.getCategories().add(mexican);
		recipes.add(guacamole);
		log.info("Created recipe \"" + guacamole.getDescription() + "\"");
		
		// Tacos
		Recipe tacos = new Recipe();
		tacos.setDescription("Spicy Grilled Chicken Tacos");
		tacos.setPrepTime(9);
		tacos.setCookTime(20);
		tacos.setDifficulty(Difficulty.MODERATE);
		tacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		tacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n"
				+ "\n"
				+ "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
				+ "\n"
				+ "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n"
				+ "\n"
				+ "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
				+ "\n"
				+ "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
				+ "\n"
				+ "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
				+ "\n"
				+ "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges");
		
		Notes tacosNote = new Notes();
		tacosNote.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n"
				+ "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n"
				+ "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n"
				+ "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n"
				+ "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
		tacos.setNotes(tacosNote);
		
		tacos.addIngredient(new Ingredient("Ancho chili powder", new BigDecimal(2), tablespoonUom));
		tacos.addIngredient(new Ingredient("Dried oregano", new BigDecimal(1), teaspoonUom));
		tacos.addIngredient(new Ingredient("Dried cumin", new BigDecimal(1), teaspoonUom));
		tacos.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom));
		tacos.addIngredient(new Ingredient("Salt", new BigDecimal("0.5"), teaspoonUom));
		tacos.addIngredient(new Ingredient("Clove garlic, finely chopped", new BigDecimal(1), eachUom));
		tacos.addIngredient(new Ingredient("Finely grated orange zest", new BigDecimal(1), tablespoonUom));
		tacos.addIngredient(new Ingredient("Fresh-squeezed orange juice", new BigDecimal(3), tablespoonUom));
		tacos.addIngredient(new Ingredient("Olive oil", new BigDecimal(2), tablespoonUom));
		tacos.addIngredient(new Ingredient("Skinless, boneless chicken thighs", new BigDecimal(4), eachUom));
		
		tacos.addIngredient(new Ingredient("Small corn tortillas", new BigDecimal(8), eachUom));
		tacos.addIngredient(new Ingredient("Packed baby arugula", new BigDecimal(3), cupUom));
		tacos.addIngredient(new Ingredient("Medium ripe avocados, sliced", new BigDecimal(2), eachUom));
		tacos.addIngredient(new Ingredient("Radishes, thinly sliced", new BigDecimal(4), eachUom));
		tacos.addIngredient(new Ingredient("Cherry tomatoes, halved", new BigDecimal("0.5"), pintUom));
		tacos.addIngredient(new Ingredient("Red onion, thinly sliced", new BigDecimal("0.25"), eachUom));
		tacos.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), tablespoonUom));
		tacos.addIngredient(new Ingredient("Sour cream thinned with 1/4 cup milk", new BigDecimal("0.5"), cupUom));
		tacos.addIngredient(new Ingredient("Lime, cut into wedges", new BigDecimal(1), eachUom));

		tacos.getCategories().add(american);
		tacos.getCategories().add(mexican);
		recipes.add(tacos);
		log.info("Created recipe \"" + tacos.getDescription() + "\"");
		
		return recipes;
	}
	
	private UnitOfMeasure getUnitOfMeasure(String description) {
		return unitOfMeasureRepository.findByDescription(description).orElseThrow(() -> new RuntimeException("Expected Unit of Measure \"" + description + "\" not found"));
	}
	private Category getCategory(String description) {
		return categoryRepository.findByDescription(description).orElseThrow(() -> new RuntimeException("Expected Category \"" + description + "\" not found"));
	}

}
