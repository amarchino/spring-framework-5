package guru.springframework.rest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.rest.api.v1.model.CategoryDTO;
import guru.springframework.rest.api.v1.model.CategoryListDTO;
import guru.springframework.rest.services.CategoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(CategoryController.BASE_URL)
//@RequestMapping("${some.url.value}")
@RequiredArgsConstructor
public class CategoryController {
	
	public static final String BASE_URL = "/api/v1/categories";
	private final CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {
		return new ResponseEntity<CategoryListDTO>(new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable("name") String name) {
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryByName(name), HttpStatus.OK);
	}
}
