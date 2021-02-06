package guru.springframework.recipeapp.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/recipe")
@Slf4j
@RequiredArgsConstructor
public class RecipeController {
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipe-form";
	private final RecipeService recipeService;

	@GetMapping("/{id}/show")
	public String showById(@PathVariable("id") String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id).block());
		return "recipe/show";
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}
	
	@GetMapping("/{id}/update")
	public String updateRecipe(@PathVariable("id") String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id).block());
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping({"", "/"})
	public String saveOrUpdate(@ModelAttribute("recipe") @Valid RecipeCommand command, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
			return RECIPE_RECIPEFORM_URL; 
		}
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("/{id}/delete")
	public String deleteRecipe(@PathVariable("id") String id, Model model) {
		log.debug("Deleting id: " + id);
		recipeService.deleteById(id).block();
		return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(NotFoundException e) {
		log.error("Handling not found exception: " + e.getMessage());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("404error");
		mav.addObject("exception", e);
		return mav;
	}
	
}
