package guru.springframework.rest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import guru.springframework.model.CustomerDTO;
import guru.springframework.rest.controllers.v1.CustomerController;
import guru.springframework.rest.domain.Customer;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CustomerMapper {
	
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	@Mapping(source = ".", target = "customerUrl", qualifiedByName = "customerUrl")
	CustomerDTO customerToCustomerDTO(Customer customer);
	Customer customerDTOToCustomer(CustomerDTO customerDto);
	
	@Named("customerUrl")
    default String customerUrl(Customer customer) {
        return CustomerController.BASE_URL + "/" + customer.getId();
    }
}
