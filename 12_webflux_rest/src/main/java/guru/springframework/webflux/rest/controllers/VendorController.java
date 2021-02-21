package guru.springframework.webflux.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.webflux.rest.domain.Vendor;
import guru.springframework.webflux.rest.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";
	private final VendorRepository vendorRepository;
	
	@GetMapping("")
	public Flux<Vendor> list() {
		return vendorRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Vendor> getById(@PathVariable String id) {
		return vendorRepository.findById(id);
	}
}
