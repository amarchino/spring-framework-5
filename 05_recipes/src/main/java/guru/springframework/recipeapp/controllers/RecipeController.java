package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/recipe")
@Slf4j
public class RecipeController {
	
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/{id}/show")
	public String showById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipe-form";
	}
	
	@GetMapping("/{id}/update")
	public String updateRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/recipe-form";
	}
	
	@PostMapping({"", "/"})
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("/{id}/delete")
	public String deleteRecipe(@PathVariable("id") Long id, Model model) {
		log.debug("Deleting id: " + id);
		recipeService.deleteById(id);
		return "redirect:/";
	}
}
