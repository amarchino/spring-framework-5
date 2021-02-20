package guru.springframework.webflux.rest.bootstrap;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.webflux.rest.domain.Category;
import guru.springframework.webflux.rest.domain.Vendor;
import guru.springframework.webflux.rest.repositories.CategoryRepository;
import guru.springframework.webflux.rest.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
//	private final CustomerRepository customerRepository;
	private final VendorRepository vendorRepository;

	@Override
	public void run(String... args) throws Exception {
		Flux.merge(
			initializeCategories(),
//			initializeCustomers(),
			initializeVendors()
		).blockLast();
		log.info("Initialization complete");
	}

	private Flux<Category> initializeCategories() {
		return categoryRepository.count()
			.flatMapMany(count -> {
				log.info("Categories Loaded before initialization = " + count);
				if(count > 0) {
					return Flux.empty();
				}
				return categoryRepository.saveAll(List.of(
					Category.builder().description("Fruits").build(),
					Category.builder().description("Dried").build(),
					Category.builder().description("Fresh").build(),
					Category.builder().description("Exotic").build(),
					Category.builder().description("Nuts").build()
				));
			});
	}
	
//	private void initializeCustomers() {
//		customerRepository.save(Customer.builder().firstname("Freddy").lastname("Meyers").build());
//		customerRepository.save(Customer.builder().firstname("Raj").lastname("Ram").build());
//		customerRepository.save(Customer.builder().firstname("Rob").lastname("Axe").build());
//		log.info("Customers Loaded = " + customerRepository.count());
//	}
	
	private Flux<Vendor> initializeVendors() {
		return vendorRepository.count()
			.flatMapMany(count -> {
				log.info("Vendors Loaded before initialization = " + count);
				if(count > 0) {
					return Flux.empty();
				}
				return vendorRepository.saveAll(List.of(
					Vendor.builder().name("Western Tasty Fruits Ltd.").build(),
					Vendor.builder().name("Exotic Fruits Company").build(),
					Vendor.builder().name("Home Fruits").build(),
					Vendor.builder().name("Fun Fresh Fruits Ltd.").build()
				));
			});
	}
}
