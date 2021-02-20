package guru.springframework.webflux.rest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.webflux.rest.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}
