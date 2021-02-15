package guru.springframework.rest.services;

import java.util.List;

import guru.springframework.rest.api.v1.model.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomerById(Long id);
}
