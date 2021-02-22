package guru.springframework.rest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import guru.springframework.rest.api.v1.model.VendorDTO;
import guru.springframework.rest.domain.Vendor;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VendorMapper {
	
	VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

	VendorDTO vendorToVendorDTO(Vendor vendor);
	Vendor vendorDTOToVendor(VendorDTO vendorDto);
}
