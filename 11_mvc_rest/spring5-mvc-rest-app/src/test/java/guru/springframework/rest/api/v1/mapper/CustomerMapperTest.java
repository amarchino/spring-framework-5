package guru.springframework.rest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import guru.springframework.rest.api.v1.model.CustomerDTO;
import guru.springframework.rest.domain.Customer;

class CustomerMapperTest {
	
	private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	void customerToCustomerDTO() {
		// Given
		Customer customer = Customer.builder().firstname("John").lastname("Doe").id(1L).build();
		// When
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
		// Then
		assertNotNull(customerDTO);
		assertEquals(customer.getId(), customerDTO.getId());
		assertEquals(customer.getFirstname(), customerDTO.getFirstname());
		assertEquals(customer.getLastname(), customerDTO.getLastname());
		assertEquals("/api/v1/customers/" + 1, customerDTO.getCustomerUrl());
	}

	@Test
	void customerDTOToCustomer() {
		// Given
		CustomerDTO customerDTO = CustomerDTO.builder().firstname("John").lastname("Doe").id(1L).build();
		// When
		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		// Then
		assertNotNull(customer);
		assertEquals(customerDTO.getId(), customer.getId());
		assertEquals(customerDTO.getFirstname(), customer.getFirstname());
		assertEquals(customerDTO.getLastname(), customer.getLastname());
	}

}
