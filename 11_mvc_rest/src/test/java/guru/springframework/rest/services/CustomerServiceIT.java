package guru.springframework.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.rest.api.v1.mapper.CustomerMapper;
import guru.springframework.rest.api.v1.model.CustomerDTO;
import guru.springframework.rest.bootstrap.Bootstrap;
import guru.springframework.rest.domain.Customer;
import guru.springframework.rest.repositories.CategoryRepository;
import guru.springframework.rest.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
public class CustomerServiceIT {

	@Autowired private CustomerRepository customerRepository;
	@Autowired private CategoryRepository categoryRepository;
	private CustomerService customerService;
	
	@BeforeEach
	public void setUp() throws Exception {
		log.info("Loading customer data: " + customerRepository.count());
		
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
		bootstrap.run();
		
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}
	
	@Test
	public void patchCustomerUpdateFirstname() {
		String updatedName = "UpdateName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();
		
		CustomerDTO customerDTO = CustomerDTO.builder().firstname(updatedName).build();
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.getOne(id);
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstname());
		assertNotEquals(originalFirstName, updatedCustomer.getFirstname());
		assertEquals(originalLastName, updatedCustomer.getLastname());
	}
	@Test
	public void patchCustomerUpdateLastname() {
		String updatedName = "UpdateName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();
		
		CustomerDTO customerDTO = CustomerDTO.builder().lastname(updatedName).build();
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.getOne(id);
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastname());
		assertEquals(originalFirstName, updatedCustomer.getFirstname());
		assertNotEquals(originalLastName, updatedCustomer.getLastname());
	}
	private Long getCustomerIdValue() {
		List<Customer> customers = customerRepository.findAll();
		log.info("Customers found: " + customers.size());
		return customers.get(0).getId();
	}
}