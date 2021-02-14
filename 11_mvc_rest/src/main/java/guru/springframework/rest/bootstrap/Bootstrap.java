package guru.springframework.rest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.rest.domain.Category;
import guru.springframework.rest.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;

	@Override
	public void run(String... args) throws Exception {
		categoryRepository.save(Category.builder().name("Fruits").build());
		categoryRepository.save(Category.builder().name("Dried").build());
		categoryRepository.save(Category.builder().name("Fresh").build());
		categoryRepository.save(Category.builder().name("Exotic").build());
		categoryRepository.save(Category.builder().name("Nuts").build());
		
		log.info("Data Loaded = " + categoryRepository.count());
	}
	
}
