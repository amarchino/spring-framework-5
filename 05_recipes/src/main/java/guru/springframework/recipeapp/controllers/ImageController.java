package guru.springframework.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipeapp.service.ImageService;
import guru.springframework.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;
	
	@GetMapping("/recipe/{id}/image")
	public String imageForm(@PathVariable Long id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "/recipe/image-upload-form";
	}
	
	@PostMapping("/recipe/{id}/image")
	public String handeImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(id, file);
		return "redirect:/recipe/"+id+"/show";
	}
}
