package guru.springframework.webflux.rest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Void> create(@RequestBody Publisher<Vendor> stream) {
		return vendorRepository.saveAll(stream).then();
	}
	
	@PutMapping("/{id}")
	public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
		vendor.setId(id);
		return vendorRepository.save(vendor);
	}
}
