package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	
	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}


	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {
		model.addAttribute("recipes", recipeService.getRecipes().collectList().block());
		log.debug("Getting index page");
		return "index";
	}
}
