package guru.springframework.rest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.rest.domain.Category;
import guru.springframework.rest.domain.Customer;
import guru.springframework.rest.repositories.CategoryRepository;
import guru.springframework.rest.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		initializeCategories();
		initializeCustomers();
		
	}

	private void initializeCategories() {
		categoryRepository.save(Category.builder().name("Fruits").build());
		categoryRepository.save(Category.builder().name("Dried").build());
		categoryRepository.save(Category.builder().name("Fresh").build());
		categoryRepository.save(Category.builder().name("Exotic").build());
		categoryRepository.save(Category.builder().name("Nuts").build());
		log.info("Categories Loaded = " + categoryRepository.count());
	}
	
	private void initializeCustomers() {
		customerRepository.save(Customer.builder().firstname("Freddy").lastname("Meyers").build());
		customerRepository.save(Customer.builder().firstname("Raj").lastname("Ram").build());
		customerRepository.save(Customer.builder().firstname("Rob").lastname("Axe").build());
		log.info("Customers Loaded = " + customerRepository.count());
	}
}
