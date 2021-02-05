package guru.springframework.recipeapp.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.recipeapp.domain.UnitOfMeasure;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

	Mono<UnitOfMeasure> findByDescription(String description);

}
