package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;

class PetTypeMapServiceTest {
	
	private PetTypeService service;
	private final Long petTypeId = Long.valueOf(1);

	@BeforeEach
	void setUp() throws Exception {
		service = new PetTypeMapService();
		service.save(PetType.builder().id(petTypeId).build());
	}

	@Test
	void savePetTypeExistingId() {
		Long id = 2L;
		PetType petType = PetType.builder().id(id).build();
		service.save(petType);
		assertEquals(id, petType.getId());
	}
	@Test
	void savePetTypeNoId() {
		PetType petType = PetType.builder().build();
		service.save(petType);
		assertNotNull(petType);
		assertNotNull(petType.getId());
		assertEquals(2L, petType.getId());
	}

	@Test
	void findAll() {
		Set<PetType> petTypes = service.findAll();
		assertEquals(1, petTypes.size());
	}

	@Test
	void findById() {
		PetType petType = service.findById(petTypeId);
		assertNotNull(petType);
		assertEquals(petTypeId, petType.getId());
	}

	@Test
	void deleteById() {
		service.deleteById(petTypeId);
		assertEquals(0L, service.findAll().size());
	}

	@Test
	void delete() {
		service.delete(service.findById(petTypeId));
		assertEquals(0L, service.findAll().size());
	}

}
