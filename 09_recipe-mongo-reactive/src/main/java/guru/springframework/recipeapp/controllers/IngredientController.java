package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

@RequiredArgsConstructor
@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService uomOfMeasureService;
	
	@GetMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable("id") String id, Model model) {
		log.debug("Getting ingredient list for recipe id " + id);
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block());
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block());
		model.addAttribute("uomList", uomOfMeasureService.listAllUoms().collectList().block());
		return "recipe/ingredient/ingredient-form";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String newRecipeIngredient(@PathVariable("recipeId") String recipeId, Model model) {
		// Make sure we have a good id value
		recipeService.findCommandById(recipeId);
		// TODO raise exception if null

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("uomList", uomOfMeasureService.listAllUoms().collectList().block());
		return "recipe/ingredient/ingredient-form";
	}
	
	@PostMapping("/recipe/{id}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();
		log.debug("Saved recipe id: " + savedCommand.getRecipeId());
		log.debug("Saved ingredient id: " + savedCommand.getId());
		return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id) {
		log.debug("Deleting ingredient id: " + id);
		ingredientService.deleteById(recipeId, id).block();
		return "redirect:/recipe/"+recipeId+"/ingredients";
	}
}
