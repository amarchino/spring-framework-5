package guru.springframework.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.rest.api.v1.mapper.CustomerMapper;
import guru.springframework.rest.api.v1.model.CustomerDTO;
import guru.springframework.rest.domain.Customer;
import guru.springframework.rest.repositories.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	private static final Long ID = 2L;
	private static final String FIRSTNAME = "Jimmy";
	private static final String LASTNAME = "Rogers";
	private static final String CUSTOMER_URL = "/shop/customers/" + ID;
	private CustomerService customerService;
	@Mock private CustomerRepository customerRepository;

	@BeforeEach
	void setUp() throws Exception {
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	void getAllCategories() {
		// Given
		List<Customer> categories = Arrays.asList(new Customer(), new Customer(), new Customer());
		when(customerRepository.findAll()).thenReturn(categories);
		// When
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
		// Then
		assertNotNull(customerDTOs);
		assertEquals(3, customerDTOs.size());
	}

	@Test
	void getCustomerByName() {
		// Given
		Customer customer = Customer.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
		// When
		CustomerDTO customerDTO = customerService.getCustomerById(ID);
		// Then
		assertNotNull(customerDTO);
		assertEquals(ID, customerDTO.getId());
		assertEquals(FIRSTNAME, customerDTO.getFirstname());
		assertEquals(LASTNAME, customerDTO.getLastname());
		assertEquals(CUSTOMER_URL, customerDTO.getCustomerUrl());
	}

	@Test
	void createNewCustomer() {
		CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).lastname(LASTNAME).build();
		Customer savedCustomer = Customer.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		// Given
		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);
		// When
		CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);
		// Then
		assertNotNull(savedCustomerDTO);
		assertEquals(ID, savedCustomerDTO.getId());
		assertEquals(FIRSTNAME, savedCustomerDTO.getFirstname());
		assertEquals(LASTNAME, savedCustomerDTO.getLastname());
		assertEquals(CUSTOMER_URL, savedCustomerDTO.getCustomerUrl());
	}
	
	@Test
	void saveCustomerByDTO() {
		CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).lastname(LASTNAME).build();
		Customer savedCustomer = Customer.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		// Given
		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);
		// When
		CustomerDTO savedCustomerDTO = customerService.saveCustomerByDTO(ID, customerDTO);
		// Then
		assertNotNull(savedCustomerDTO);
		assertEquals(ID, savedCustomerDTO.getId());
		assertEquals(FIRSTNAME, savedCustomerDTO.getFirstname());
		assertEquals(LASTNAME, savedCustomerDTO.getLastname());
		assertEquals(CUSTOMER_URL, savedCustomerDTO.getCustomerUrl());
	}
	
	@Test
	void deleteCustomerById() {
		customerService.deleteCustomerById(ID);
		verify(customerRepository, times(1)).deleteById(Mockito.anyLong());
	}
}
