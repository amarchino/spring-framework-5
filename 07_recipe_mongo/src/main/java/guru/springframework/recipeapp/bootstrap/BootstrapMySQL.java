package guru.springframework.recipeapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.recipeapp.domain.Category;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.CategoryRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile({"dev", "prod"})
@RequiredArgsConstructor
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {
	
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(categoryRepository.count() == 0) {
			log.debug("Loading Categories");
			loadCategories();
		}
		if(unitOfMeasureRepository.count() == 0) {
			log.debug("Loading Unit of Measures");
			loadUom();
		}
		log.debug("Loaded bootstrap data");
	}
	
	private void loadCategories() {
		categoryRepository.save(Category.builder().description("American").build());
		categoryRepository.save(Category.builder().description("Italian").build());
		categoryRepository.save(Category.builder().description("Mexican").build());
		categoryRepository.save(Category.builder().description("Fast Food").build());
	}
	
	private void loadUom() {
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Teaspoon").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Tablespoon").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Cup").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Pinch").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Ounce").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Each").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Dash").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().description("Pint").build());
	}
	
}
