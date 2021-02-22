package guru.springframework.rest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import guru.springframework.rest.api.v1.model.CustomerDTO;
import guru.springframework.rest.domain.Customer;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CustomerMapper {
	
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	CustomerDTO customerToCustomerDTO(Customer customer);
	Customer customerDTOToCustomer(CustomerDTO customerDto);
}
