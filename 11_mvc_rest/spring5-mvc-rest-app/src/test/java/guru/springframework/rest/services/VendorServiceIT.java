package guru.springframework.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.rest.api.v1.mapper.VendorMapper;
import guru.springframework.rest.api.v1.model.VendorDTO;
import guru.springframework.rest.bootstrap.Bootstrap;
import guru.springframework.rest.domain.Vendor;
import guru.springframework.rest.repositories.CategoryRepository;
import guru.springframework.rest.repositories.CustomerRepository;
import guru.springframework.rest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
public class VendorServiceIT {

	@Autowired private CustomerRepository customerRepository;
	@Autowired private CategoryRepository categoryRepository;
	@Autowired private VendorRepository vendorRepository;
	private VendorService vendorService;
	
	@BeforeEach
	public void setUp() throws Exception {
		log.info("Loading vendor data: " + vendorRepository.count());
		
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
		bootstrap.run();
		
		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}
	
	@Test
	public void patchVendorUpdateName() {
		String updatedName = "UpdateName";
		Long id = getVendorIdValue();
		
		Vendor originalVendor = vendorRepository.getOne(id);
		assertNotNull(originalVendor);
		String originalName = originalVendor.getName();
		
		VendorDTO vendorDTO = VendorDTO.builder().name(updatedName).build();
		vendorService.patchVendor(id, vendorDTO);
		
		Vendor updatedVendor = vendorRepository.getOne(id);
		assertNotNull(updatedVendor);
		assertEquals(updatedName, updatedVendor.getName());
		assertNotEquals(originalName, updatedVendor.getName());
	}
	private Long getVendorIdValue() {
		List<Vendor> vendors = vendorRepository.findAll();
		log.info("Vendors found: " + vendors.size());
		return vendors.get(0).getId();
	}
}
