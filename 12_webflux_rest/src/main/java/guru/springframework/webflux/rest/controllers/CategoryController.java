package guru.springframework.webflux.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.webflux.rest.domain.Category;
import guru.springframework.webflux.rest.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private final CategoryRepository categoryRepository;
	
	@GetMapping("")
	public Flux<Category> list() {
		return categoryRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Category> getById(@PathVariable String id) {
		return categoryRepository.findById(id);
	}
}
