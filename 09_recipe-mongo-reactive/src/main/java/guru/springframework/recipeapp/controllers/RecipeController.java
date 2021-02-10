package guru.springframework.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/recipe")
@Slf4j
@RequiredArgsConstructor
public class RecipeController {
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipe-form";
	private final RecipeService recipeService;
	
	private WebDataBinder webDataBinder;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}

	@GetMapping("/{id}/show")
	public String showById(@PathVariable("id") String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}
	
	@GetMapping("/{id}/update")
	public String updateRecipe(@PathVariable("id") String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping({"", "/"})
	public Mono<Object> saveOrUpdate(@ModelAttribute("recipe") RecipeCommand command) {
		webDataBinder.validate();
		BindingResult bindingResult = webDataBinder.getBindingResult();
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
			return Mono.just(RECIPE_RECIPEFORM_URL); 
		}
		
		return recipeService
				.saveRecipeCommand(command)
				.map(savedCommand -> "redirect:/recipe/" + savedCommand.getId() + "/show");
	}
	
	@GetMapping("/{id}/delete")
	public Mono<String> deleteRecipe(@PathVariable("id") String id, Model model) {
		log.debug("Deleting id: " + id);
		return recipeService.deleteById(id).thenReturn("redirect:/");
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFound(NotFoundException e, Model model) {
		log.error("Handling not found exception: " + e.getMessage());
		model.addAttribute("exception", e);
		return "404error";
	}
	
}
