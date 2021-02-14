package guru.springframework.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.rest.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
