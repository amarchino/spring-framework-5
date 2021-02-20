package guru.springframework.webflux.rest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.webflux.rest.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
