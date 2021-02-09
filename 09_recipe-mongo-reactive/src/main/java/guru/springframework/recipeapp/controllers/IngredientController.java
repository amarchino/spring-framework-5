package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.service.IngredientService;
import guru.springframework.recipeapp.service.RecipeService;
import guru.springframework.recipeapp.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Controller
public class IngredientController {

	private static final String INGREDIENT_INGREDIENT_FORM_URL = "recipe/ingredient/ingredient-form";
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService uomOfMeasureService;

	private WebDataBinder webDataBinder;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}
	
	@GetMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable("id") String id, Model model) {
		log.debug("Getting ingredient list for recipe id " + id);
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		model.addAttribute("uomList", uomOfMeasureService.listAllUoms());
		return INGREDIENT_INGREDIENT_FORM_URL;
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public Mono<String> newRecipeIngredient(@PathVariable("recipeId") String recipeId, Model model) {
		// Make sure we have a good id value
		return recipeService.findCommandById(recipeId)
			.map(r -> {
				// TODO raise exception if null
				
				IngredientCommand ingredientCommand = new IngredientCommand();
				ingredientCommand.setRecipeId(recipeId);
				ingredientCommand.setUom(new UnitOfMeasureCommand());
				
				model.addAttribute("ingredient", ingredientCommand);
				model.addAttribute("uomList", uomOfMeasureService.listAllUoms());
				return INGREDIENT_INGREDIENT_FORM_URL;
			});
	}
	
	@PostMapping("/recipe/{id}/ingredient")
	public Mono<String> saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand ingredientCommand, Model model) {
		webDataBinder.validate();
		BindingResult bindingResult = webDataBinder.getBindingResult();
		if(bindingResult.hasErrors()) {
			model.addAttribute("uomList", uomOfMeasureService.listAllUoms());
			bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
			return Mono.just(INGREDIENT_INGREDIENT_FORM_URL);
		}
		
		return ingredientService.saveIngredientCommand(ingredientCommand)
				.doOnNext(savedCommand -> {
					log.debug("Saved recipe id: " + savedCommand.getRecipeId());
					log.debug("Saved ingredient id: " + savedCommand.getId());
				})
				.map(savedCommand -> "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show");
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
	public Mono<String> deleteIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id) {
		log.debug("Deleting ingredient id: " + id);
		return ingredientService.deleteById(recipeId, id).thenReturn("redirect:/recipe/"+recipeId+"/ingredients");
	}
}
