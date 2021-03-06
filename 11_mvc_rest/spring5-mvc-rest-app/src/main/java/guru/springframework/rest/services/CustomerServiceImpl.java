package guru.springframework.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import guru.springframework.model.CustomerDTO;
import guru.springframework.rest.api.v1.mapper.CustomerMapper;
import guru.springframework.rest.domain.Customer;
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
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer.setId(id);
		return saveAndReturnDTO(customer);
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		customer = customerRepository.save(customer);
		return customerMapper.customerToCustomerDTO(customer);
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id)
			.map(customer -> {
				if(customerDTO.getFirstname() != null) {
					customer.setFirstname(customerDTO.getFirstname());
				}
				if(customerDTO.getLastname() != null) {
					customer.setLastname(customerDTO.getLastname());
				}
				return customerRepository.save(customer);
			})
			.map(customerMapper::customerToCustomerDTO)
			.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
}
