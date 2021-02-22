package guru.springframework.rest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import guru.springframework.model.VendorDTO;
import guru.springframework.rest.controllers.v1.VendorController;
import guru.springframework.rest.domain.Vendor;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VendorMapper {
	
	VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

	@Mapping(source = ".", target = "vendorUrl", qualifiedByName = "vendorUrl")
	VendorDTO vendorToVendorDTO(Vendor vendor);
	Vendor vendorDTOToVendor(VendorDTO vendorDto);
	
	@Named("vendorUrl")
    default String vendorUrl(Vendor vendor) {
        return VendorController.BASE_URL + "/" + vendor.getId();
    }
}
