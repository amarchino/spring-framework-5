package guru.springframework.restdocs.web.mappers;

import org.mapstruct.Mapper;

import guru.springframework.restdocs.domain.Beer;
import guru.springframework.restdocs.web.model.BeerDto;

@Mapper(uses = { DateMapper.class })
public interface BeerMapper {

	BeerDto beerToBeerDto(Beer beer);
	Beer beerDtoToBeer(BeerDto beerDto);
}
