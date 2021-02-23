package guru.springframework.restdocs.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import guru.springframework.restdocs.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
