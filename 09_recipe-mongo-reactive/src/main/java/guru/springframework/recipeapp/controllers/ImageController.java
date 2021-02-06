package guru.springframework.recipeapp.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.service.ImageService;
import guru.springframework.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;
	
	@GetMapping("/recipe/{id}/image")
	public String imageForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id).block());
		return "/recipe/image-upload-form";
	}
	
	@PostMapping("/recipe/{id}/image")
	public String handeImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(id, file);
		return "redirect:/recipe/"+id+"/show";
	}
	
	@GetMapping("/recipe/{id}/recipeimage")
	public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(id).block();
		if (recipeCommand.getImage() == null) {
			return;
		}
		byte[] byteArray = new byte[recipeCommand.getImage().length];
		int i = 0;
		for(Byte b : recipeCommand.getImage()) {
			byteArray[i++] = b;
		}
		response.setContentType("Image/jpeg");
		InputStream is = new ByteArrayInputStream(byteArray);
		IOUtils.copy(is, response.getOutputStream());
	}
}
