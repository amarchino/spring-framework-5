package guru.springframework.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.recipeapp.domain.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
