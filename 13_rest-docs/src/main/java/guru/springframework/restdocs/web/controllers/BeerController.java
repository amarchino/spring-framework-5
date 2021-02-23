package guru.springframework.restdocs.web.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.restdocs.repositories.BeerRepository;
import guru.springframework.restdocs.web.mappers.BeerMapper;
import guru.springframework.restdocs.web.model.BeerDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BeerController.BASE_URL)
@RequiredArgsConstructor
public class BeerController {

	public static final String BASE_URL = "/api/v1/beer";
	private final BeerMapper beerMapper;
	private final BeerRepository beerRepository;
	
	@GetMapping("/{beerId}")
	@ResponseStatus(HttpStatus.OK)
	public BeerDto getBeerById(@PathVariable("beerId") UUID beerId) {
		return beerMapper.beerToBeerDto(beerRepository.findById(beerId).get());
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveNewBeer(@RequestBody @Validated BeerDto beerDto) {
		beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
	}
	@PutMapping("/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void saveNewBeer(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto) {
		beerRepository.findById(beerId)
			.ifPresent(beer -> {
				beer.setBeerName(beerDto.getBeerName());
				beer.setBeerStyle(beerDto.getBeerStyle().name());
				beer.setPrice(beerDto.getPrice());
				beer.setUpc(beerDto.getUpc());
				beerRepository.save(beer);
			});
	}
}
