package guru.springframework.rest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.model.CustomerDTO;
import guru.springframework.model.CustomerListDTO;
import guru.springframework.rest.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(description = "This is my customer controller", name = "CustomerController")
@RestController
@RequestMapping(CustomerController.BASE_URL)
@RequiredArgsConstructor
public class CustomerController {
	public static final String BASE_URL = "/api/v1/customers";
	private final CustomerService customerService;
	
	@Operation(summary = "This will get a list of customers.", description = "These are some notes about the API.")
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public CustomerListDTO getAllCustomers() {
		CustomerListDTO customerListDTO = new CustomerListDTO();
		customerListDTO.getCustomers().addAll(customerService.getAllCustomers());
		return customerListDTO;
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CustomerDTO getCustomerById(@PathVariable("id") Long id) {
		return customerService.getCustomerById(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerService.createNewCustomer(customerDTO);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CustomerDTO updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
		return customerService.saveCustomerByDTO(id, customerDTO);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CustomerDTO patchCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
		return customerService.patchCustomer(id, customerDTO);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteCustomer(@PathVariable("id") Long id) {
		customerService.deleteCustomerById(id);
	}
}
