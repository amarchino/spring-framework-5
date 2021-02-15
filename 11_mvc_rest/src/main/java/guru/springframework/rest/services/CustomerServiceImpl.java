package guru.springframework.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import guru.springframework.rest.api.v1.mapper.CustomerMapper;
import guru.springframework.rest.api.v1.model.CustomerDTO;
import guru.springframework.rest.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository
			.findAll()
			.stream()
			.map(customerMapper::customerToCustomerDTO)
			.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO)
				.orElseThrow(() -> new RuntimeException("Not found: " + id));
	}

}
