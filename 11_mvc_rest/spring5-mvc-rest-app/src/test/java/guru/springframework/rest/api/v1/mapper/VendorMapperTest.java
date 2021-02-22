package guru.springframework.rest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import guru.springframework.model.VendorDTO;
import guru.springframework.rest.domain.Vendor;

class VendorMapperTest {
	
	private static final long ID = 1L;
	private static final String NAME = "John";
	private VendorMapper mapper = VendorMapper.INSTANCE;

	@Test
	void vendorToVendorDTO() {
		// Given
		Vendor vendor = Vendor.builder().name(NAME).id(ID).build();
		// When
		VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);
		// Then
		assertNotNull(vendorDTO);
		assertEquals(vendor.getId(), vendorDTO.getId());
		assertEquals(vendor.getName(), vendorDTO.getName());
		assertEquals("/api/v1/vendors/" + ID, vendorDTO.getVendorUrl());
	}

	@Test
	void vendorDTOToVendor() {
		// Given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		vendorDTO.setId(1L);
		// When
		Vendor vendor = mapper.vendorDTOToVendor(vendorDTO);
		// Then
		assertNotNull(vendor);
		assertEquals(vendorDTO.getId(), vendor.getId());
		assertEquals(vendorDTO.getName(), vendor.getName());
	}

}
