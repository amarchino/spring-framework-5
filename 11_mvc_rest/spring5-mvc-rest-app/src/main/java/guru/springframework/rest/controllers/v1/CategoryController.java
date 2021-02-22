package guru.springframework.rest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.model.CategoryDTO;
import guru.springframework.model.CategoryListDTO;
import guru.springframework.rest.services.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CategoryController.BASE_URL)
//@RequestMapping("${some.url.value}")
@RequiredArgsConstructor
public class CategoryController {
	
	public static final String BASE_URL = "/api/v1/categories";
	private final CategoryService categoryService;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public CategoryListDTO getAllCategories() {
		CategoryListDTO categoryListDTO = new CategoryListDTO();
		categoryListDTO.getCategories().addAll(categoryService.getAllCategories());
		return categoryListDTO;
	}

	@GetMapping("/{name}")
	@ResponseStatus(code = HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable("name") String name) {
		return categoryService.getCategoryByName(name);
	}
}
