package guru.springframework.webflux.rest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.webflux.rest.domain.Category;
import guru.springframework.webflux.rest.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	public static final String BASE_URL = "/api/v1/categories";
	private final CategoryRepository categoryRepository;
	
	@GetMapping
	public Flux<Category> list() {
		return categoryRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Category> getById(@PathVariable String id) {
		return categoryRepository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Void> create(@RequestBody Publisher<Category> stream) {
		return categoryRepository.saveAll(stream).then();
	}
	
	@PutMapping("/{id}")
	public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
		category.setId(id);
		return categoryRepository.save(category);
	}
	
	@PatchMapping("/{id}")
	public Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
		return categoryRepository.findById(id)
			.flatMap(c -> {
				if(!category.getDescription().equals(c.getDescription())) {
					return categoryRepository.save(c);
				}
				return Mono.just(c);
			});
	}
}
