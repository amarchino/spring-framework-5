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

import guru.springframework.model.VendorDTO;
import guru.springframework.rest.api.v1.mapper.VendorMapper;
import guru.springframework.rest.domain.Vendor;
import guru.springframework.rest.repositories.VendorRepository;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {
	private static final Long ID = 2L;
	private static final String NAME = "Franks Fresh Fruits from France Ltd.";
	private static final String VENDOR_URL = "/api/v1/vendors/" + ID;
	private VendorService vendorService;
	@Mock private VendorRepository vendorRepository;
	private VendorDTO vendorDTO;

	@BeforeEach
	void setUp() throws Exception {
		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
		vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
	}

	@Test
	void getAllCategories() {
		// Given
		List<Vendor> categories = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
		when(vendorRepository.findAll()).thenReturn(categories);
		// When
		List<VendorDTO> vendorDTOs = vendorService.getAllVendors();
		// Then
		assertNotNull(vendorDTOs);
		assertEquals(3, vendorDTOs.size());
	}

	@Test
	void getVendorByName() {
		// Given
		Vendor vendor = Vendor.builder().id(ID).name(NAME).build();
		when(vendorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(vendor));
		// When
		VendorDTO vendorDTO = vendorService.getVendorById(ID);
		// Then
		assertNotNull(vendorDTO);
		assertEquals(ID, vendorDTO.getId());
		assertEquals(NAME, vendorDTO.getName());
		assertEquals(VENDOR_URL, vendorDTO.getVendorUrl());
	}

	@Test
	void createNewVendor() {
		
		Vendor savedVendor = Vendor.builder().id(ID).name(NAME).build();
		// Given
		when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(savedVendor);
		// When
		VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);
		// Then
		assertNotNull(savedVendorDTO);
		assertEquals(ID, savedVendorDTO.getId());
		assertEquals(NAME, savedVendorDTO.getName());
		assertEquals(VENDOR_URL, savedVendorDTO.getVendorUrl());
	}
	
	@Test
	void saveVendorByDTO() {
		Vendor savedVendor = Vendor.builder().id(ID).name(NAME).build();
		// Given
		when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(savedVendor);
		// When
		VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID, vendorDTO);
		// Then
		assertNotNull(savedVendorDTO);
		assertEquals(ID, savedVendorDTO.getId());
		assertEquals(NAME, savedVendorDTO.getName());
		assertEquals(VENDOR_URL, savedVendorDTO.getVendorUrl());
	}
	
	@Test
	void deleteVendorById() {
		vendorService.deleteVendorById(ID);
		verify(vendorRepository, times(1)).deleteById(Mockito.anyLong());
	}
}
