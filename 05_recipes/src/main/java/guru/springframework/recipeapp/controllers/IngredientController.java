package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import guru.springframework.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	
	@GetMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable("id") Long id, Model model) {
		log.debug("Getting ingredient list for recipe id " + id);
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/ingredient/list";
	}
}
