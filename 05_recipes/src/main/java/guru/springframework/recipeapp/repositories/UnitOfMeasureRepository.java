package guru.springframework.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.recipeapp.domain.UnitOfMeasure;

@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

}
